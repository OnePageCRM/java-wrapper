# OnePageCRM Java API Wrapper
This project is a comprehensive java API wrapper aimed to abstract some of the difficulties associated with getting started interacting with external APIs, providing you quick and easy access to API resources in useful formats.

The project uses gradle, an advanced, general purpose build management system.  This allows for streamlined functionality, such as automatically including jars in the buid path or running unit tests much more quickly.

So far, it only contains a moderately-sized subsection of calls and functions available using the API, though more are currently being added.

## Getting started

- Clone the repository.

- Import the project into your IDE.

- Create a config.properties file containing your username and password.

## Example
The following is an example of a method which will:
- Log in a user.
- Display details about the user and their account.
- Get their Action Stream.
- Get their a-to-z list of Contacts.
- Get their list of Deals.
- Pick the first Contact. 
- And add a new Call for that Contact.
- Create a new Contact.

```java

  public static void main(String[] args) {

    // Login 
    User loggedInUser = User.login("username", "password");

    // Display all the details about the user / account.
    LOG.info("Logged in User : " + loggedInUser);
    LOG.info("User's Team : " + loggedInUser.getAccount().team);
    LOG.info("User's Settings : " + Account.settings);
    LOG.info("User's Statuses : " + loggedInUser.getAccount().statuses);
    LOG.info("User's Lead Sources : " + loggedInUser.getAccount().leadSources);
    LOG.info("User's Custom Fields : " + loggedInUser.getAccount().customFields);
    LOG.info("User's Company Fields : " + loggedInUser.getAccount().companyFields);
    LOG.info("User's Call Results : " + loggedInUser.getAccount().callResults);
    LOG.info("User's Filters : " + loggedInUser.getAccount().filters);
    LOG.info("User's ContactsCounts : " + loggedInUser.getAccount().contactsCount);
    LOG.info("User's StreamCount : " + loggedInUser.getAccount().streamCount);
    LOG.info("User's Predefined Actions : " + loggedInUser.getAccount().predefinedActions);
    LOG.info("User's Contact Titles : " + loggedInUser.getAccount().contactTitles);
    LOG.info("User's Account Rights : " + loggedInUser.getAccountRights());

    // Get user's Action Stream
    ContactList stream = loggedInUser.actionStream();

    // Get user's list of contacts in alphabetical order
    ContactList contacts = loggedInUser.contacts();

    // Get user's list of deals (pipeline)
    DealList pipeline = loggedInUser.pipeline();

    // Pick the first contact from the Action Stream
    Contact contact = stream.get(0);

    if (contact.isValid()) {

      // Get the list of Actions associated with that contact
      List<Action> actions = contact.getActions();

      // Get the Next Action specifically
      Action nextAction = contact.getNextAction();

      // Create a new Call resource
      Call newCall = new Call()
            .setCallResult(new CallResult()
            .setId("interested")
            .setText("From Java Wrapper..."));
            .save();

      Contact newContact = new Contact()
            .setLastName("Myles")
            .setCompanyName("Myles Inc.")
            .setFirstName("Cillian");
            .save();
    }
  }
}
```

## Example
The following is an example of a code snippet which will:
- Log in a user.
- Pick their first contact.
- Add a new deal for that contact.

```java
    User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        Contact first = loggedInUser.actionStream().get(0);

        new Deal()
             .setStage(10)
             .setStatus("pending")
             .setContactId(first.getId())
             .setAmount(33.33d)
             .setDate(new Date())
             .setText("Java Wrapper Deal Text")
             .setName("Java Wrapper Deal Name")
             .save();
```
