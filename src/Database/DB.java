
package Database;


public class DB {
    public static void main(String[] args){
        String Query = "CREATE TABLE `DB`.`Elements` ( `id` INT(10) NOT NULL AUTO_INCREMENT, `MTI` VARCHAR(4) NOT NULL ";
        String Fields = "";    
        for(int i=2;i<129;i++){
            Fields =Fields + ", `Field"+i+"` TEXT(999) ";
        }
        String remain=",PRIMARY KEY (id)) ENGINE = InnoDB;";
        Query = Query+Fields+remain;
        System.out.println(Query);
    }
    
}
