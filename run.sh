#!/bin/bash

#====================
#   Colors section  #
#====================

COLOR_RESET='\033[0m'

# Regular Colors
BLACK='\033[0;30m'
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
WHITE='\033[0;37m'

# Bold
BOLD_BLACK='\033[1;30m'
BOLD_RED='\033[1;31m'
BOLD_GREEN='\033[1;32m'
BOLD_YELLOW='\033[1;33m'
BOLD_BLUE='\033[1;34m'
BOLD_PURPLE='\033[1;35m'
BOLD_CYAN='\033[1;36m'
BOLD_WHITE='\033[1;37m'

#=====================
#  Variables section #
#=====================

#Database
expected_database_provider='postgresql'
expected_lines_count=3
temporary_lines_count=0
temporary_database_data=''
database_name=''
database_username=''
database_password=''
database_provider=''
database_host=''
database_port='' 

#Redis
temporary_redis_data=''
redis_host=''
redis_port=''


#====================
#   Code section    #
#====================
main(){
	 set -x
	readPropertiesFileForDatabase
	readPropertiesFileForRedis
	checkPortIsInUse
	checkDocker
	checkHostNames
	buildProject
	runDockerCompose
}

readPropertiesFileForRedis(){
	readPropertiesRedisData
	printReadedRedisData
	extractReadedRedisDataToVariables
	checkExtractedRedisData
}

readPropertiesFileForDatabase(){
	checkFileExists
	readPropertiesDatabaseData
	checkPropertiesDatabaseData
	printReadedDatabaseData
	extractReadedDatabaseDataToVariables
	checkExtractedDatabaseData
}



runDockerCompose(){
	echo -e "${BOLD_PURPLE}Start docker compose${COLOR_RESET}"
	docker compose up -d
	status=$?
	if [ $status -ne 0 ];
	then
		echo -e "${BOLD_RED}Something went wrong with docker compose${COLOR_RESET}"
		exit 1
	else
		echo -e "${BOLD_GREEN}Docker compose done ${COLOR_RESET}"
	fi

}

checkHostNames(){
	if [ $database_host != "postgres" ];
	then
		echo -e "${BOLD_RED}Postgres host should be 'postgres' ! ${COLOR_RESET}"
		exit 1
	fi

	if [ $redis_host != "redis" ];
	then
		echo -e "${BOLD_RED}Redis host should be 'redis' ! ${COLOR_RESET}"
		exit 1
	fi
}

buildProject(){
	echo -e "${BOLD_PURPLE}Start build project${COLOR_RESET}"
	mvn clean > /dev/null
	mvn install -DskipTests > /dev/null
	if [ $? -ne 0 ];
	then
		echo -e "${BOLD_RED}Something went wrong with mvn build... ${COLOR_RESET}"
		exit 1
	else
		echo -e "${BOLD_GREEN}Build project done${COLOR_RESET}"
	fi
}

checkPortIsInUse(){
	check_port_data=$(sudo ss -tulpn | grep -i listen | grep "$redis_port");
	if [ $? -ne 0 ];
	then
		echo -e "${BOLD_GREEN}Redis port $redis_port is not in use, continue...${COLOR_RESET}"
	else
		echo -e "${BOLD_RED}Port $redis_port is in use${COLOR_RESET}"
		exit 1
	fi

	check_port_data=$(sudo ss -tulpn | grep -i listen | grep "$database_port");
	if [ $? -ne 0 ];
	then
		echo -e "${BOLD_GREEN}Database port $database_port is not in use, continue...${COLOR_RESET}"
	else
		echo -e "${BOLD_RED}Port $database_port is in use${COLOR_RESET}"
		exit 1
	fi

}

checkDocker(){
	check_docker_daemon=$(sudo docker ps);
	if [ $? -ne 0 ];
	then
		echo -e "${BOLD_RED}Docker deamon is not running !${COLOR_RESET}"
		exit 1
	else
		echo -e "${BOLD_GREEN}Docker daemon is running, continue....${COLOR_RESET}"
	fi
}

checkFileExists(){
	if [ ! -f "./web/src/main/resources/application-dev.yml" ]; then
		echo -e "${BOLD_RED}Properties file not found !"
		exit 1
	fi
}

readPropertiesDatabaseData(){
	while read line
	do
		if echo "$line" | egrep -i "url|username|password" > /dev/null
		then
			temporary_database_data="$temporary_database_data$line\n"
			temporary_lines_count=$((temporary_lines_count + 1))
		fi
	done < ./web/src/main/resources/application-dev.yml
}

readPropertiesRedisData(){
	while read line
	do
		if echo "$line" | egrep -i "^\s*host:|^\s*port:" > /dev/null
		then
			temporary_redis_data="$temporary_redis_data$line\n"
		fi
	done < ./web/src/main/resources/application-dev.yml
}

checkPropertiesDatabaseData(){
	if [ "$temporary_lines_count" -ne $expected_lines_count ]; then
		echo -e "${BOLD_RED}Properties file contains more than one ($temporary_lines_count) url/username/password database section !"
		exit 1
	fi
}

printReadedDatabaseData(){
	echo -e "${GREEN}Readed database data: \n${COLOR_RESET}$temporary_database_data"
}

printReadedRedisData(){
	echo -e "${GREEN}Readed redis data: \n${COLOR_RESET}$temporary_redis_data"
}

extractReadedDatabaseDataToVariables(){
	result=$(echo -e "$temporary_database_data" | mawk -F'[:/]' '{print $3,$6,$7,$8}' | mawk '{print $1,$2,$3,$4}')
	database_provider=$(echo -e "$result" | mawk '{print $1}' | sed 's/ //g')
	database_host=$(echo -e "$result" | mawk '{print $2}' | sed 's/ //g')
	database_port=$(echo -e "$result" | mawk '{print $3}' | sed 's/ //g')
	database_name=$(echo -e "$result" | mawk '{print $4}' | sed 's/ //g')
	database_login=$(echo -e "$temporary_database_data" | grep 'username' | mawk -F'[:]' '{print $2}'| sed 's/ //g')
	database_password=$(echo -e "$temporary_database_data" | grep 'password' | mawk -F'[:]' '{print $2}'| sed 's/ //g')
}

extractReadedRedisDataToVariables(){
	redis_host=$(echo -e "$temporary_redis_data" | grep 'host' | mawk -F'[:]' '{print $2}' | tr -d ' ')
	redis_port=$(echo -e "$temporary_redis_data" | grep 'port' | mawk -F'[:]' '{print $2}' | tr -d ' ')
}

checkExtractedDatabaseData(){
	echo -e "${YELLOW}Extracting database data..."
	# Check database provider
	if [ "$database_provider" != "$expected_database_provider" ]; then
		echo -e "${BOLD_RED}Wrong database provider ! only $expected_database_provider is compatible !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Database provider: ${COLOR_RESET}$database_provider"
	fi

	# Check database host
	if [ ! -n "$database_host" ]; then
		echo -e "${BOLD_RED}Database host cannot be empty !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Database host: ${COLOR_RESET}$database_host"
	fi

	# Check database port
	if [ ! -n "$database_port" ]; then
		echo -e "${BOLD_RED}Database port cannot be empty !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Database port: ${COLOR_RESET}$database_port"
	fi		

	# Check database name
	if [ ! -n "$database_name" ]; then
		echo -e "${BOLD_RED}Database name cannot be empty !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Database name: ${COLOR_RESET}$database_name"
	fi	

	# Check database login
	if [ ! -n "$database_login" ]; then
		echo -e "${BOLD_RED}Database login cannot be empty !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Database login: ${COLOR_RESET}$database_login \n"
	fi	

}

checkExtractedRedisData(){
	echo -e "${YELLOW}Extracting redis data..."
	
	# Check redis host
	if [ ! -n "$redis_host" ]; then
		echo -e "${BOLD_RED}Redis host cannot be empty !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Redis host: ${COLOR_RESET}$redis_host"
	fi

	# Check redis port
	if [ ! -n "$redis_port" ]; then
		echo -e "${BOLD_RED}Redis port cannot be empty !"
		exit 1
	else
		echo -e "${BOLD_GREEN}Redis port: ${COLOR_RESET}$redis_port"
	fi	
}

echo -e "${BOLD_GREEN}Running E-dziennik application health check... ${COLOR_RESET} \n"
main
