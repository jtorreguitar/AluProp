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

SITE_URL = 'http://localhost:8080';

options = webdriver.ChromeOptions();
options.binary_location = '/Applications/Google Chrome Canary.app/Contents/MacOS/Google Chrome Canary';
options.add_argument('window-size=1280x2500');
options.add_argument('headless');

webDriver = webdriver.Chrome(chrome_options=options);

print "Getting website...";
webDriver.get(SITE_URL);

#Wait for elements to load and then fetch the sign up button
time.sleep(3);
webDriver.save_screenshot('homePage.png');
buttonSignUp = webDriver.find_element_by_xpath('//a[contains(@href, \'signUp\')]');
print("On homepage. Clicking sign up button...");
buttonSignUp.click();


#Wait for elements to load, take screenshot and fetch form input elements
time.sleep(3);
webDriver.save_screenshot('signUpPage.png');

textFieldMail = webDriver.find_element_by_xpath('//input[@id=\'email\']');
textFieldPassword = webDriver.find_element_by_xpath('//input[@id=\'password\']');
textFieldRepeatPassword = webDriver.find_element_by_xpath('//input[@id=\'repeatPassword\']');
textFieldName = webDriver.find_element_by_xpath('//input[@id=\'name\']');
textFieldLastName = webDriver.find_element_by_xpath('//input[@id=\'lastName\']');
textFieldPhoneNumber = webDriver.find_element_by_xpath('//input[@id=\'phoneNumber\']');
textFieldBirthDate = webDriver.find_element_by_xpath('//input[@id=\'birthDate\']');
textAreaBio = webDriver.find_element_by_xpath('//textarea[@id=\'bio\']');
selectGender = Select(webDriver.find_element_by_xpath('//select[@id=\'gender\']'));
selectUserRole = Select(webDriver.find_element_by_xpath('//select[@id=\'select-role\']'));
buttonRegisterSubmit = webDriver.find_element_by_xpath('//button[@id=\'btn-register\']');

print("On sign Up page. Filling in user information and clicking the register button...");

#We only want to fill in university/career information if the user will be a student,
#and both career and university selects will show up after selecting a role.
#Thus, we select the role and only then fetch both input elements and fill their information in.
selectUserRole.select_by_value(INFO_ROLE_CODE);
if (INFO_ROLE_CODE == '0'):
	selectUniversity = Select(webDriver.find_element_by_xpath('//select[@id=\'select-university\']'));
	selectCareer = Select(webDriver.find_element_by_xpath('//select[@id=\'select-career\']'));
	selectUniversity.select_by_visible_text(INFO_UNIVERSITY);
	selectCareer.select_by_visible_text(INFO_CAREER);


textFieldMail.send_keys(INFO_MAIL);
textFieldPassword.send_keys(INFO_PASSWORD);
textFieldRepeatPassword.send_keys(INFO_PASSWORD);
textFieldName.send_keys(INFO_NAME);
textFieldLastName.send_keys(INFO_SURNAME);
textFieldPhoneNumber.send_keys(INFO_PHONE_NUMBER);
textFieldBirthDate.send_keys(INFO_BIRTH_DATE);
textAreaBio.send_keys(INFO_BIO);
selectGender.select_by_value(INFO_GENDER_CODE);

buttonRegisterSubmit.click();
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