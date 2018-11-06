import { element, by, ElementFinder } from 'protractor';

export class PartnerUserComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-partner-user div table .btn-danger'));
    title = element.all(by.css('jhi-partner-user div h2#page-heading span')).first();

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

export class PartnerUserUpdatePage {
    pageTitle = element(by.id('jhi-partner-user-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    partnerSelect = element(by.id('field_partner'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async partnerSelectLastOption() {
        await this.partnerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async partnerSelectOption(option) {
        await this.partnerSelect.sendKeys(option);
    }

    getPartnerSelect(): ElementFinder {
        return this.partnerSelect;
    }

    async getPartnerSelectedOption() {
        return this.partnerSelect.element(by.css('option:checked')).getText();
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

export class PartnerUserDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-partnerUser-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-partnerUser'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
