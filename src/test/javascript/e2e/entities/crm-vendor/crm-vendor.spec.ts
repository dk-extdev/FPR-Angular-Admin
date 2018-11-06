/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CrmVendorComponentsPage, CrmVendorDeleteDialog, CrmVendorUpdatePage } from './crm-vendor.page-object';

const expect = chai.expect;

describe('CrmVendor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let crmVendorUpdatePage: CrmVendorUpdatePage;
    let crmVendorComponentsPage: CrmVendorComponentsPage;
    let crmVendorDeleteDialog: CrmVendorDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CrmVendors', async () => {
        await navBarPage.goToEntity('crm-vendor');
        crmVendorComponentsPage = new CrmVendorComponentsPage();
        expect(await crmVendorComponentsPage.getTitle()).to.eq('Crm Vendors');
    });

    it('should load create CrmVendor page', async () => {
        await crmVendorComponentsPage.clickOnCreateButton();
        crmVendorUpdatePage = new CrmVendorUpdatePage();
        expect(await crmVendorUpdatePage.getPageTitle()).to.eq('Create or edit a Crm Vendor');
        await crmVendorUpdatePage.cancel();
    });

    it('should create and save CrmVendors', async () => {
        const nbButtonsBeforeCreate = await crmVendorComponentsPage.countDeleteButtons();

        await crmVendorComponentsPage.clickOnCreateButton();
        await crmVendorUpdatePage.setNameInput('name');
        expect(await crmVendorUpdatePage.getNameInput()).to.eq('name');
        await crmVendorUpdatePage.apiTypeSelectLastOption();
        await crmVendorUpdatePage.setWebsiteInput('website');
        expect(await crmVendorUpdatePage.getWebsiteInput()).to.eq('website');
        await crmVendorUpdatePage.save();
        expect(await crmVendorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await crmVendorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CrmVendor', async () => {
        const nbButtonsBeforeDelete = await crmVendorComponentsPage.countDeleteButtons();
        await crmVendorComponentsPage.clickOnLastDeleteButton();

        crmVendorDeleteDialog = new CrmVendorDeleteDialog();
        expect(await crmVendorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Crm Vendor?');
        await crmVendorDeleteDialog.clickOnConfirmButton();

        expect(await crmVendorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
