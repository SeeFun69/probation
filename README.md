# All You Need and Project Specs :
1. Spring Boot Version 3.0.5
2. JDK Version 17
3. MySQL Database
4. Maven
5. IDE
6. Database Management Tools (DBeaver)
7. RabbitMq
8. Redis

# How To Run :
1. Clone Repository URL on your local computer
2. Create Database name probation, Please ensure that the password and username are replaced with your own username and password.
3. Run Redis on your local machine/virtual machine
4. Run RabbitMQ on your local machine/virtual machine, Please ensure that the password and username are replaced with your own username and password.
5. Make sure that port 9191 is not in use
6. Run ProjectProbationApplication
6. Done

# If you want to use SMS notif
1. Uncomment this line in SaleController
2. ![image](https://github.com/SeeFun69/Employee-crud/assets/86507298/35ceb036-6cc3-4e2d-a669-59cde9dba6cb)
3. if you get this error
4. ![Auth sms notif](https://github.com/SeeFun69/probation/assets/86507298/22963342-6dd3-4a39-8eae-d4fc7dd8336d)
5. its because the token is expired
6. ![Twillio](https://github.com/SeeFun69/probation/assets/86507298/86579cc2-b25c-4a41-8153-07c2b227521b)
7. why expired? because it's FREE, you must replace it with new one
8. get the token from the Twillio console:
9. ![Twillio Console](https://github.com/SeeFun69/probation/assets/86507298/801ce208-5127-4b21-ab8a-38dce1aeff8f)
10. you must register twillio first, here is the reference : https://www.twilio.com/blog/server-notifications-java-spring

# Testing Steps
1. If there are no issues when running the APK, you can import the file "Probation.postman_collection.json" into your Postman workspace
2. After a successful import, please register yourself at
3. ![Register with token](https://github.com/SeeFun69/probation/assets/86507298/3106ab42-0dbc-463f-8512-79a487539dfd)
4. I assume that you know how to set the global token using the environment, so you can set the token obtained from registration
5. Or, you can log in again to make sure that the account we registered can indeed be used to log in to the platform
6. ![auth login](https://github.com/SeeFun69/probation/assets/86507298/d685dc27-b77c-4b5e-ac53-8b5abcfa1eca)
7. After you successfully log in and set the token, the next step is to add stock
8. ![Add Stock](https://github.com/SeeFun69/probation/assets/86507298/b44fc8b4-708b-41a8-808b-144309326971)
9. Please check in the `getAllStock` endpoint to confirm that the stock we added is in the database
10. ![Check Stock](https://github.com/SeeFun69/probation/assets/86507298/52ed07a0-b768-414e-9e34-4cb0bc0f3892)
11. After that, go to `FlashSaleTrigger` to start the flash sale. Here we will register the limit for how much stock we will offer for the flash sale
12. ![Flash begin](https://github.com/SeeFun69/probation/assets/86507298/cb4ac3b6-160a-47df-9e03-c2158e4af6c5)
13. ![redis stock](https://github.com/SeeFun69/probation/assets/86507298/f64ed32b-067c-4118-bb85-0c17bf8e90a5)
14. After that, go to `SaleUsingRedis&Rabitmq` to place an order for the flash sale
15. ![flash sale](https://github.com/SeeFun69/probation/assets/86507298/cf3f0cc6-6ee7-4561-8adc-bd0681eef580)
16. ![after](https://github.com/SeeFun69/probation/assets/86507298/c318dca6-c32a-4298-9d86-71117b703cd6)
17. ![after db](https://github.com/SeeFun69/probation/assets/86507298/30772b01-f26b-424c-8207-580e5b4ef23c)
