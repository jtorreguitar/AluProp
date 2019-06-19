# AluProp

## Summary

Aluprop is a simple page that joins students wishing to achieve their goals of living close to their university with people willing to rent out properties to them.

The application has two different types of user, host and guest. Once a host publishes a property a user is allowed to show interest in it which in turn allows the user to create a proposal to rent the property. The guest can create a proposal on his own or inviting other guests to join in. If any of them decline, the proposal is cancelled. If they all accept the host then receives the proposal and can choose to accept or decline it. If the host accepts then contact information is shared between the proposal's invited users and the host in order to allow them to settle the final details in regards to the renting out of the property.

## Code

The page uses spring web-mvc with hibernate as its ORM and spring security to handle user permissions. The front is made using jsp pages and the bootstrap library. The architecture is 3-tiered with controller, service and data access objects layers.

## application.properties

Some configuration constants which may vary from user to user are kept in an application properties file that is listed in our .gitignore file. The file should define these constants:

db.username = _database_username_

db.password = _database_password_

db.name = _name_of_your_database

rememberme.key = _string_for_spring_security_remember_me_key_

this last one can be any value you choose. However, in production it should be set to a sufficiently long randomly generated string to ensure safety. The file itself should be put in the persistence module under src/main/resources.

## Credentials for testing

These three users should be sufficient to test out all of the functionalities of the application.

**Host accounts**
- email: nachovidau96@gmail.com pass:123456

**User accounts**
- email: ividaurreta@itba.edu.ar pass:123456
- email: jtorreguitar@itba.edu.ar pass:12345aA_
