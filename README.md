# Order Management System
Order Management System project is a Java application simulating the ones used in bars and restaurants.
## Key Features
* **Order placing:** Adding and confirming in very simple way.
* **Programmable buttons:** Add up to 24 positions from your menu.
* **Order history:** See your recent orders.
## Getting Started
* Clone this repository or download it as a ZIP archive.
* Navigate to the folder in terminal.
* Build the app using ```./gradlew build```.
* Run the app using ```./gradlew run```. 
## Usage
* Add your menu using "Set Menu Positions" button.
* When creating orders add items to an order by clicking the buttons with their name on it.
* Confirm the order by clicking "Confirm Order" button.
* Check your order in order history by clicking "Order History" button.

## Screenshots
![Menu](https://github.com/user-attachments/assets/19e13284-a9d2-4097-bfb2-ecad1ba25a16)
![Button Programming](https://github.com/user-attachments/assets/8cfcd014-ccd9-4c80-8be4-2de329559926)
![Order Confirmation](https://github.com/user-attachments/assets/47fa76e4-358b-491b-b3a0-46e696b55f45)
![OMOrderhistory](https://github.com/user-attachments/assets/57d291b4-99ed-46b0-afe6-7df79b2b1499)

## Database
If you want to ensure your project is working properly I suggest you make a mySQL database similar to this one:


![OMdatabase](https://github.com/user-attachments/assets/011b82a9-766a-4991-a3e3-a2dde5fc4b4e)

,where:
* price and orderprice are DECIMAL(10,2),
* name is a VARCHAR(100),
* itemID and ID are INT Primary Key,
* order is LONG TEXT.
