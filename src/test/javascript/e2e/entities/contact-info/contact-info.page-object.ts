import { element, by, ElementFinder } from 'protractor';

export class ContactInfoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contact-info div table .btn-danger'));
    title = element.all(by.css('jhi-contact-info div h2#page-heading span')).first();

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

export class ContactInfoUpdatePage {
    pageTitle = element(by.id('jhi-contact-info-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    primaryPhoneInput = element(by.id('field_primaryPhone'));
    secondaryPhoneInput = element(by.id('field_secondaryPhone'));
    workPhoneInput = element(by.id('field_workPhone'));
    workPhoneExtInput = element(by.id('field_workPhoneExt'));
    mobilePhoneInput = element(by.id('field_mobilePhone'));
    skypeIdInput = element(by.id('field_skypeId'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setPrimaryPhoneInput(primaryPhone) {
        await this.primaryPhoneInput.sendKeys(primaryPhone);
    }

    async getPrimaryPhoneInput() {
        return this.primaryPhoneInput.getAttribute('value');
    }

    async setSecondaryPhoneInput(secondaryPhone) {
        await this.secondaryPhoneInput.sendKeys(secondaryPhone);
    }

    async getSecondaryPhoneInput() {
        return this.secondaryPhoneInput.getAttribute('value');
    }

    async setWorkPhoneInput(workPhone) {
        await this.workPhoneInput.sendKeys(workPhone);
    }

    async getWorkPhoneInput() {
        return this.workPhoneInput.getAttribute('value');
    }

    async setWorkPhoneExtInput(workPhoneExt) {
        await this.workPhoneExtInput.sendKeys(workPhoneExt);
    }

    async getWorkPhoneExtInput() {
        return this.workPhoneExtInput.getAttribute('value');
    }

    async setMobilePhoneInput(mobilePhone) {
        await this.mobilePhoneInput.sendKeys(mobilePhone);
    }

    async getMobilePhoneInput() {
        return this.mobilePhoneInput.getAttribute('value');
    }

    async setSkypeIdInput(skypeId) {
        await this.skypeIdInput.sendKeys(skypeId);
    }

    async getSkypeIdInput() {
        return this.skypeIdInput.getAttribute('value');
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
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

export class ContactInfoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contactInfo-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contactInfo'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
