# coding=utf-8
import time
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support.ui import Select
import sys

INFO_MAIL = sys.argv[1];
INFO_PASSWORD = sys.argv[2];

INFO_PROPERTY_IMG_2 = '/Users/Bensas/ITBA/PAW/newPull/AluProp/SeleniumTesting/room.jpg';
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

print ("Fetching homepage...")
time.sleep(3);
webDriver.save_screenshot('CreateProperty - homePage.png');
buttonLogIn = webDriver.find_element_by_xpath('//a[contains(@href, \'logIn\')]');
print("On homepage. Clicking log in button...");
buttonLogIn.click();


#Wait for elements to load, take screenshot and fetch form input elements
time.sleep(3);
webDriver.save_screenshot('CreateProperty - logInPage.png');
textFieldMail = webDriver.find_element_by_xpath('//input[@id=\'email\']');
textFieldPassword = webDriver.find_element_by_xpath('//input[@id=\'password\']');
buttonLoginSubmit = webDriver.find_element_by_xpath('//button[@id=\'btn-login\']');

textFieldMail.send_keys(INFO_MAIL);
textFieldPassword.send_keys(INFO_PASSWORD);
buttonLoginSubmit.click();

time.sleep(3);
webDriver.save_screenshot('CreateProperty - homePageAfterLogin.png');

#We are now logged in
try:
	publishPropertyButton = webDriver.find_element_by_xpath('//a[contains(@href, \'/host/create\')]');
	publishPropertyButton.click();
except NoSuchElementException:
	print "The mail and password provided do not belong to en existing host account.";
	webDriver.close();
	webDriver.quit();
	exit(1);
print("Clicked Publish Property button...");

time.sleep(3);

webDriver.save_screenshot('CreateProperty - publishPropertyPage.png');
print("Entering Property info...");

buttonUploadPicture2 = webDriver.find_element_by_xpath('//input[@id=\'uploadBtn2\']');
textFieldPropertyName = webDriver.find_element_by_xpath('//input[contains(@class, \'property-name\')]');
textFieldPropertyDescription = webDriver.find_element_by_xpath('//textarea[@id=\'caption\']');
selectPropertyType = Select(webDriver.find_element_by_xpath('//select[@id=\'select-property-type\']'));
selectPropertyNeighborhood = Select(webDriver.find_element_by_xpath('//select[@id=\'select-neighbourhood\']'));
selectPropertyPrivacy = Select(webDriver.find_element_by_xpath('//select[@id=\'select-privacy-level\']'));
textFieldPropertyCapacity = webDriver.find_element_by_xpath('//input[contains(@class, \'create-capacity\')]');
textFieldPropertyPrice = webDriver.find_element_by_xpath('//input[@id=\'price\']');

buttonUploadPicture2.send_keys(INFO_PROPERTY_IMG_2);
textFieldPropertyName.send_keys(INFO_PROPERTY_NAME);
textFieldPropertyDescription.send_keys(INFO_PROPERTY_DESCRIPTION);
selectPropertyType.select_by_value(INFO_PROPERTY_TYPE);
selectPropertyNeighborhood.select_by_value(INFO_PROPERTY_NEIGHBORHOOD);
selectPropertyPrivacy.select_by_value(INFO_PROPERTY_PRIVACY_LEVEL);
textFieldPropertyCapacity.send_keys(INFO_PROPERTY_CAPACITY);
textFieldPropertyPrice.clear();
textFieldPropertyPrice.send_keys(INFO_PROPERTY_PRICE);

buttonPublishSubmit = webDriver.find_element_by_xpath('//button[@id=\'btn-publish\']');
buttonPublishSubmit.click();
print("Clicked Publish Property(SUBMIT) button...");
time.sleep(3);
webDriver.save_screenshot('CreateProperty - propertyPageAfterPublish.png');
try:
	myProfileButton = webDriver.find_element_by_xpath('//button[contains(@class, \'pause-property\')]');
	print "Property created successfully!";
except NoSuchElementException:
	print "Proposal creation failed! Check screenshots for more info.";
webDriver.close();
webDriver.quit();