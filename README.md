# USCDoorDrink

USC DoorDrink
Team #42
Authors: Angela Sun, Tiffany Chen, Madison Brading 

# EMULATOR INSTRUCTIONS:
Pixel 3a API 32

# run instructions
run the app. this takes you to the login screen.
if you do not have an account, click "No Account?". This will prompt you to create an account.
If you already have an account, log in.
If you've forgotten your password, click "Forgot Password?". This prompts you to enter your email, and sends an email to that email address to reset the password.
Click outside the keyboard or the down arrow in the lower left corner to lower the keyboard if necessary.

# once you've logged in
On the upper left corner, there is a button that opens up a menu of options.

# merchant view:
after logging in, the merchant and user views are different. below is for the merchant view:
### view map
View Map takes the user to a map view. Click the target button on the upper right to go to your current location.
The markers may take a moment to load. Please be patient!
If necessary, there are zoom in and zoom out controls on the lower right.
Merchant addresses in the database are automatically set as markers to the map.
Clicking the marker lets the user view the store's name, address, phone number, and menu.

### edit menu
this button enables merchants to add, update, and delete their menu items.
to add a drink, press the add button and enter the drink name, price, and caffeine. then press enter
to delete a drink, press the x button next to the drink on the right.

### order history
this button takes the user to their user profile.

### profile
this button takes the user to their profile.
for merchants, profile is where their information such as name, address, and phone number are set.
the marker will not appear on the map until the merchant enters their address and presses update.

### logout
this button logs the user out.


## user view:
below is for the user view

### view map
View Map takes the user to a map view. Click the target button on the upper right to go to your current location.
The markers may take a moment to load. Please be patient!
If necessary, there are zoom in and zoom out controls on the lower right.
Merchant addresses in the database are automatically set as markers to the map.
Clicking the marker lets the user view the store's name, address, phone number, and menu.
From there, the user can add drinks to their cart.
If they add a drink that results their total caffeine count they ordered for the day to exceed the recommended daily amount, they get an alert.
They can then check out the cart and place their order, which will take them to a delivery progress page.



### stores


### delivery progress
this allows the user to see information about their delivery if they currently have a delivery


### order history
this takes the user to a view of their order history.


### about us
this takes the user to an 'about us' page detailing information about the ap


### profile
this button takes the user to their profile.
for users, they will be able to see and edit their name, email and phone number.
press update after editing!


### logout
this button logs the user out.

### new updates
- Implementation of basic features such as showing biking and driving route from current location to store
- UI changes such as showing category names before the editText
- Added toasts to notify user an action has been completed after adding to cart, checking out, etc
- Additional features such as:
      - User can filter menu items from best match, price low-to-high, price high-to-low, caffeine high-to-low and caffeine low-to-high
      - User gets drink recommendation based on the weather of their current location with multiple different scenarios possible depending on whether weather is less than 40 degrees fahrenheit, between 40 and 72 degrees fahrenheit, between 72 and 85 degrees fahrenheit, and above 85 degrees fahrenheit.
      - User can check out a comprehensive view of their order history through swiping between tabs and then seeing a graphical representation of their order history
      - Merchant can check out a comprehensive view of their order history through swiping between tabs and then seeing a graphical representation of their customer order history



