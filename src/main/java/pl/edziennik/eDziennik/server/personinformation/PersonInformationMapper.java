package pl.edziennik.eDziennik.server.personinformation;

public class PersonInformationMapper {

    private PersonInformationMapper(){

    }

    public static PersonInformation mapToPersonInformation(String firstName, String lastName, String pesel){
        PersonInformation personInformation = new PersonInformation();
        personInformation.setPesel(pesel);
        personInformation.setFirstName(firstName);
        personInformation.setLastName(lastName);
        return personInformation;
    }

}
