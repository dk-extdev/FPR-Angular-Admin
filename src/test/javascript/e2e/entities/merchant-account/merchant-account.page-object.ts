import { element, by, ElementFinder } from 'protractor';

export class MerchantAccountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-merchant-account div table .btn-danger'));
    title = element.all(by.css('jhi-merchant-account div h2#page-heading span')).first();

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

export class MerchantAccountUpdatePage {
    pageTitle = element(by.id('jhi-merchant-account-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    midInput = element(by.id('field_mid'));
    midDescriptorInput = element(by.id('field_midDescriptor'));
    activeInput = element(by.id('field_active'));
    merchantSelect = element(by.id('field_merchant'));
    clientCrmSelect = element(by.id('field_clientCrm'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setMidInput(mid) {
        await this.midInput.sendKeys(mid);
    }

    async getMidInput() {
        return this.midInput.getAttribute('value');
    }

    async setMidDescriptorInput(midDescriptor) {
        await this.midDescriptorInput.sendKeys(midDescriptor);
    }

    async getMidDescriptorInput() {
        return this.midDescriptorInput.getAttribute('value');
    }

    getActiveInput() {
        return this.activeInput;
    }

    async merchantSelectLastOption() {
        await this.merchantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async merchantSelectOption(option) {
        await this.merchantSelect.sendKeys(option);
    }

    getMerchantSelect(): ElementFinder {
        return this.merchantSelect;
    }

    async getMerchantSelectedOption() {
        return this.merchantSelect.element(by.css('option:checked')).getText();
    }

    async clientCrmSelectLastOption() {
        await this.clientCrmSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clientCrmSelectOption(option) {
        await this.clientCrmSelect.sendKeys(option);
    }

    getClientCrmSelect(): ElementFinder {
        return this.clientCrmSelect;
    }

    async getClientCrmSelectedOption() {
        return this.clientCrmSelect.element(by.css('option:checked')).getText();
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

export class MerchantAccountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-merchantAccount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-merchantAccount'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
