/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IndustryComponentsPage, IndustryDeleteDialog, IndustryUpdatePage } from './industry.page-object';

const expect = chai.expect;

describe('Industry e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let industryUpdatePage: IndustryUpdatePage;
    let industryComponentsPage: IndustryComponentsPage;
    let industryDeleteDialog: IndustryDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Industries', async () => {
        await navBarPage.goToEntity('industry');
        industryComponentsPage = new IndustryComponentsPage();
        expect(await industryComponentsPage.getTitle()).to.eq('Industries');
    });

    it('should load create Industry page', async () => {
        await industryComponentsPage.clickOnCreateButton();
        industryUpdatePage = new IndustryUpdatePage();
        expect(await industryUpdatePage.getPageTitle()).to.eq('Create or edit a Industry');
        await industryUpdatePage.cancel();
    });

    it('should create and save Industries', async () => {
        const nbButtonsBeforeCreate = await industryComponentsPage.countDeleteButtons();

        await industryComponentsPage.clickOnCreateButton();
        await industryUpdatePage.setNameInput('name');
        expect(await industryUpdatePage.getNameInput()).to.eq('name');
        await industryUpdatePage.setDescriptionInput('description');
        expect(await industryUpdatePage.getDescriptionInput()).to.eq('description');
        await industryUpdatePage.save();
        expect(await industryUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await industryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Industry', async () => {
        const nbButtonsBeforeDelete = await industryComponentsPage.countDeleteButtons();
        await industryComponentsPage.clickOnLastDeleteButton();

        industryDeleteDialog = new IndustryDeleteDialog();
        expect(await industryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Industry?');
        await industryDeleteDialog.clickOnConfirmButton();

        expect(await industryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
