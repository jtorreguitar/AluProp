# coding=utf-8
import time
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support.ui import Select
import sys

registeredUsersFile = open("numOfRegisteredUsers.txt", "r+");
numOfRegisteredUsers = registeredUsersFile.read();
print numOfRegisteredUsers;
registeredUsersFile.close();

INFO_NAME = 'Peter' + numOfRegisteredUsers;
INFO_SURNAME = 'Gabriel' + numOfRegisteredUsers;
INFO_MAIL = sys.argv[1];
INFO_PASSWORD = sys.argv[2];
INFO_PHONE_NUMBER = '1822928392';
INFO_BIRTH_DATE = '1997-09-07';
INFO_UNIVERSITY =  'UTN';
INFO_CAREER = 'Ingeniería informática';
INFO_BIO = 'I like turtles ' + numOfRegisteredUsers;
INFO_GENDER_CODE = '1';
INFO_ROLE_CODE = '1' if sys.argv[3]=='host' else '0';

INFO_PROPERTY_IMG_2 = '/Users/Bensas/ITBA/PAW/newPull/AluProp/SeleniumTestingds'
INFO_PROPERTY_NAME = 'Departamento en Pilar';
INFO_PROPERTY_DESCRIPTION = 'Hermoso monoambiente ideal para programadores. Rincon de lectura y buena conexion a internet.';
INFO_PROPERTY_TYPE = '0';
INFO_PROPERTY_NEIGHBORHOOD = '2';
INFO_PROPERTY_PRIVACY_LEVEL = '0';
INFO_PROPERTY_CAPACITY = '3';
INFO_PROPERTY_PRICE = '3000';

SITE_URL = 'http://localhost:8080';

options = webdriver.ChromeOptions();
options.binary_location = '/Applications/Google Chrome Canary.app/Contents/MacOS/Google Chrome Canary';
options.add_argument('window-size=1280x2500');
options.add_argument('headless');

webDriver = webdriver.Chrome(chrome_options=options);

webDriver.get(SITE_URL);

#Wait for elements to load and then fetch them
time.sleep(3);
webDriver.save_screenshot('homePage.png');
buttonSignUp = webDriver.find_element_by_xpath('//a[contains(@href, \'signUp\')]');
#WebDriverWait(webDriver, 100).until(EC.visibility_of(buttonLogin));

#Log accordingly and take action on the elements
print("Sign Up button appeared. Clicking it...");
buttonSignUp.click();

#Take a screenshot of login page and go to register page
time.sleep(3);
webDriver.save_screenshot('signUpPage.png');

textFieldMail = webDriver.find_element_by_xpath('//input[@id=\'email\']');
textFieldPassword = webDriver.find_element_by_xpath('//input[@id=\'password\']');
textFieldRepeatPassword = webDriver.find_element_by_xpath('//input[@id=\'repeatPassword\']');
textFieldName = webDriver.find_element_by_xpath('//input[@id=\'name\']');
textFieldLastName = webDriver.find_element_by_xpath('//input[@id=\'lastName\']');
textFieldPhoneNumber = webDriver.find_element_by_xpath('//input[@id=\'phoneNumber\']');
textFieldBirthDate = webDriver.find_element_by_xpath('//input[@id=\'birthDate\']');
selectUniversity = Select(webDriver.find_element_by_xpath('//select[@id=\'select-university\']'));
selectCareer = Select(webDriver.find_element_by_xpath('//select[@id=\'select-career\']'));
textAreaBio = webDriver.find_element_by_xpath('//textarea[@id=\'bio\']');
selectGender = Select(webDriver.find_element_by_xpath('//select[@id=\'gender\']'));
selectUserRole = Select(webDriver.find_element_by_xpath('//select[@id=\'role\']'));

buttonRegisterSubmit = webDriver.find_element_by_xpath('//button[@id=\'btn-register\']');

print("Register(submit) button appeared. Filling in user information and clicking it...");
textFieldMail.send_keys(INFO_MAIL);
textFieldPassword.send_keys(INFO_PASSWORD);
textFieldRepeatPassword.send_keys(INFO_PASSWORD);
textFieldName.send_keys(INFO_NAME);
textFieldLastName.send_keys(INFO_SURNAME);
textFieldPhoneNumber.send_keys(INFO_PHONE_NUMBER);
textFieldBirthDate.send_keys(INFO_BIRTH_DATE);
selectUniversity.select_by_visible_text(INFO_UNIVERSITY);
selectCareer.select_by_visible_text(INFO_CAREER);
textAreaBio.send_keys(INFO_BIO);
selectGender.select_by_value(INFO_GENDER_CODE);
selectUserRole.select_by_value(INFO_ROLE_CODE);

buttonRegisterSubmit.click();
print("Clicked register button...");
time.sleep(8);
webDriver.save_screenshot('homePageAfterRegistration.png');

try:
	myProfileButton = webDriver.find_element_by_xpath('//img[contains(@src, \'bell.png\')]');
	print "User registered successfully!";
except NoSuchElementException:
	print "User registration failed! Check screenshot for more info.";
registeredUsersFile = open("numOfRegisteredUsers.txt", "w");
registeredUsersFile.write(str(int(numOfRegisteredUsers)+1));
registeredUsersFile.close();
webDriver.close();