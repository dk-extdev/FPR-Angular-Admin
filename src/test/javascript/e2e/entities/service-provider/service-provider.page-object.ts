import { element, by, ElementFinder } from 'protractor';

export class ServiceProviderComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-service-provider div table .btn-danger'));
    title = element.all(by.css('jhi-service-provider div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ServiceProviderUpdatePage {
    pageTitle = element(by.id('jhi-service-provider-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descriptionInput = element(by.id('field_description'));
    addressInput = element(by.id('field_address'));
    stateInput = element(by.id('field_state'));
    postalCodeInput = element(by.id('field_postalCode'));
    countryInput = element(by.id('field_country'));
    phoneNumberInput = element(by.id('field_phoneNumber'));
    phoneExtInput = element(by.id('field_phoneExt'));
    emailInput = element(by.id('field_email'));
    websiteInput = element(by.id('field_website'));
    currencyInput = element(by.id('field_currency'));
    activeInput = element(by.id('field_active'));
    cityInput = element(by.id('field_city'));
    industrySelect = element(by.id('field_industry'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
    }

    async setStateInput(state) {
        await this.stateInput.sendKeys(state);
    }

    async getStateInput() {
        return this.stateInput.getAttribute('value');
    }

    async setPostalCodeInput(postalCode) {
        await this.postalCodeInput.sendKeys(postalCode);
    }

    async getPostalCodeInput() {
        return this.postalCodeInput.getAttribute('value');
    }

    async setCountryInput(country) {
        await this.countryInput.sendKeys(country);
    }

    async getCountryInput() {
        return this.countryInput.getAttribute('value');
    }

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
    }

    async setPhoneExtInput(phoneExt) {
        await this.phoneExtInput.sendKeys(phoneExt);
    }

    async getPhoneExtInput() {
        return this.phoneExtInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setWebsiteInput(website) {
        await this.websiteInput.sendKeys(website);
    }

    async getWebsiteInput() {
        return this.websiteInput.getAttribute('value');
    }

    async setCurrencyInput(currency) {
        await this.currencyInput.sendKeys(currency);
    }

    async getCurrencyInput() {
        return this.currencyInput.getAttribute('value');
    }

    getActiveInput() {
        return this.activeInput;
    }
    async setCityInput(city) {
        await this.cityInput.sendKeys(city);
    }

    async getCityInput() {
        return this.cityInput.getAttribute('value');
    }

    async industrySelectLastOption() {
        await this.industrySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async industrySelectOption(option) {
        await this.industrySelect.sendKeys(option);
    }

    getIndustrySelect(): ElementFinder {
        return this.industrySelect;
    }

    async getIndustrySelectedOption() {
        return this.industrySelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ServiceProviderDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-serviceProvider-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-serviceProvider'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
