import { element, by, ElementFinder } from 'protractor';

export class GuruClientComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-guru-client div table .btn-danger'));
    title = element.all(by.css('jhi-guru-client div h2#page-heading span')).first();

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

export class GuruClientUpdatePage {
    pageTitle = element(by.id('jhi-guru-client-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    levelSelect = element(by.id('field_level'));
    guruSelect = element(by.id('field_guru'));
    clientSelect = element(by.id('field_client'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setLevelSelect(level) {
        await this.levelSelect.sendKeys(level);
    }

    async getLevelSelect() {
        return this.levelSelect.element(by.css('option:checked')).getText();
    }

    async levelSelectLastOption() {
        await this.levelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async guruSelectLastOption() {
        await this.guruSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async guruSelectOption(option) {
        await this.guruSelect.sendKeys(option);
    }

    getGuruSelect(): ElementFinder {
        return this.guruSelect;
    }

    async getGuruSelectedOption() {
        return this.guruSelect.element(by.css('option:checked')).getText();
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

export class GuruClientDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-guruClient-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-guruClient'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
