package arqsoft.user.model;

import javax.persistence.*;


@Entity
@Table( name = "user" )
@NamedQueries( { @NamedQuery( name = User.FIND_ALL, query = "SELECT u FROM User u" ),
                 @NamedQuery( name = User.FIND_BY_EMAIL,
                 query = "SELECT u FROM User u WHERE u.email = :email" ) } )
public class User{

    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_BY_EMAIL = "User.findByEmail";

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public long getId( ){
        return id;
    }

    public void setId( long id ){
        this.id = id;
    }

    public String getFirstName( ){
        return firstName;
    }

    public void setFirstName( String name ){
        this.firstName = name;
    }

    public String getLastName( ){
        return lastName;
    }

    public void setLastName( String lastName ){
        this.lastName = lastName;
    }

    public String getEmail( ){
        return email;
    }

    public void setEmail( String email ){
        this.email = email;
    }

    public String getPassword( ){
        return password;
    }

    public void setPassword( String password ){
        this.password = password;
    }

}
