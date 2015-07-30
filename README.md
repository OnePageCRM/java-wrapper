# OnePageCRM Java API Wrapper
This project is (will be) a comprehensive java project aimed to abstract some of the difficulties associated with getting started interacting with external APIs, providing you quick and easy access to API resources in useful formats.

So far, it only contains a small subsection of calls and functions available using the API, though more are currently being added.

## Getting started

- Clone the repository.

- Open Eclipse and **Import** > **General** > **Existing Projects into Workspace**.

- Navigate to the cloned repository and Import.

- Right click the project and click **Build Path** > **Configure Build Path**.
  -  Add both of the following folders as source folders:
    - java-client-wrapper/src/main/java
    - java-client-wrapper/src/test/java

- Download latest version of org.json-XXXXXXXX.jar from [here] (https://code.google.com/p/org-json-java/downloads/list) .
  - Import this (**Add External JARs...**) to the project.

## Example
The following is an example of an executable class which will:
- Login a user.
- Get their Action Stream.
- Pick the first contact. 
- And add a new call for that contact.

```java
package com.onepagecrm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.onepagecrm.models.Call;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;

public class Driver {

	private static final Logger LOG = Logger.getLogger(Driver.class.getName());
	
	public static void main(String[] args) {
		
		Properties prop = new Properties();
		InputStream input = null;
				
		try {
			input = new FileInputStream("config.properties");
	 
			// Load the properties file
			prop.load(input);
	 
		} catch (IOException e) {
			LOG.severe("Error loading the config.properties file");
			LOG.severe(e.toString());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOG.severe("Error closing the config.properties file");
					LOG.severe(e.toString());
				}
			}
		}

		// Login 
		User loggedInUser = User.login(
				prop.getProperty("username"), 
				prop.getProperty("password"));

		// Get user's Action Stream
		ContactList contacts = loggedInUser.actionStream();
		
		// Pick first contact
		Contact contact = contacts.get(0);
		
		// Create a call
		Call newCall = new Call()
				.setCallResult("interested")
				.setNote("JAVA_CLIENT");
		
		// Add the call		
		newCall.save(contact)
	}
}
```
