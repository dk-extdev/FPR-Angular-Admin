import { element, by, ElementFinder } from 'protractor';

export class CrmVendorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-crm-vendor div table .btn-danger'));
    title = element.all(by.css('jhi-crm-vendor div h2#page-heading span')).first();

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

export class CrmVendorUpdatePage {
    pageTitle = element(by.id('jhi-crm-vendor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    apiTypeSelect = element(by.id('field_apiType'));
    websiteInput = element(by.id('field_website'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setApiTypeSelect(apiType) {
        await this.apiTypeSelect.sendKeys(apiType);
    }

    async getApiTypeSelect() {
        return this.apiTypeSelect.element(by.css('option:checked')).getText();
    }

    async apiTypeSelectLastOption() {
        await this.apiTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setWebsiteInput(website) {
        await this.websiteInput.sendKeys(website);
    }

    async getWebsiteInput() {
        return this.websiteInput.getAttribute('value');
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

export class CrmVendorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-crmVendor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-crmVendor'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
