import { element, by, ElementFinder } from 'protractor';

export class ClientCrmComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-client-crm div table .btn-danger'));
    title = element.all(by.css('jhi-client-crm div h2#page-heading span')).first();

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

export class ClientCrmUpdatePage {
    pageTitle = element(by.id('jhi-client-crm-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    endpointInput = element(by.id('field_endpoint'));
    webUsernameInput = element(by.id('field_webUsername'));
    webPasswordInput = element(by.id('field_webPassword'));
    apiKeyInput = element(by.id('field_apiKey'));
    apiSecretInput = element(by.id('field_apiSecret'));
    clientSelect = element(by.id('field_client'));
    vendorSelect = element(by.id('field_vendor'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setEndpointInput(endpoint) {
        await this.endpointInput.sendKeys(endpoint);
    }

    async getEndpointInput() {
        return this.endpointInput.getAttribute('value');
    }

    async setWebUsernameInput(webUsername) {
        await this.webUsernameInput.sendKeys(webUsername);
    }

    async getWebUsernameInput() {
        return this.webUsernameInput.getAttribute('value');
    }

    async setWebPasswordInput(webPassword) {
        await this.webPasswordInput.sendKeys(webPassword);
    }

    async getWebPasswordInput() {
        return this.webPasswordInput.getAttribute('value');
    }

    async setApiKeyInput(apiKey) {
        await this.apiKeyInput.sendKeys(apiKey);
    }

    async getApiKeyInput() {
        return this.apiKeyInput.getAttribute('value');
    }

    async setApiSecretInput(apiSecret) {
        await this.apiSecretInput.sendKeys(apiSecret);
    }

    async getApiSecretInput() {
        return this.apiSecretInput.getAttribute('value');
    }

    async clientSelectLastOption() {
        await this.clientSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clientSelectOption(option) {
        await this.clientSelect.sendKeys(option);
    }

    getClientSelect(): ElementFinder {
        return this.clientSelect;
    }

    async getClientSelectedOption() {
        return this.clientSelect.element(by.css('option:checked')).getText();
    }

    async vendorSelectLastOption() {
        await this.vendorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async vendorSelectOption(option) {
        await this.vendorSelect.sendKeys(option);
    }

    getVendorSelect(): ElementFinder {
        return this.vendorSelect;
    }

    async getVendorSelectedOption() {
        return this.vendorSelect.element(by.css('option:checked')).getText();
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

export class ClientCrmDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-clientCrm-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-clientCrm'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
