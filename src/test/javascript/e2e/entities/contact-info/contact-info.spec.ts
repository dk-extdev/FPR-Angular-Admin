/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactInfoComponentsPage, ContactInfoDeleteDialog, ContactInfoUpdatePage } from './contact-info.page-object';

const expect = chai.expect;

describe('ContactInfo e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactInfoUpdatePage: ContactInfoUpdatePage;
    let contactInfoComponentsPage: ContactInfoComponentsPage;
    /*let contactInfoDeleteDialog: ContactInfoDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContactInfos', async () => {
        await navBarPage.goToEntity('contact-info');
        contactInfoComponentsPage = new ContactInfoComponentsPage();
        expect(await contactInfoComponentsPage.getTitle()).to.eq('Contact Infos');
    });

    it('should load create ContactInfo page', async () => {
        await contactInfoComponentsPage.clickOnCreateButton();
        contactInfoUpdatePage = new ContactInfoUpdatePage();
        expect(await contactInfoUpdatePage.getPageTitle()).to.eq('Create or edit a Contact Info');
        await contactInfoUpdatePage.cancel();
    });

    /* it('should create and save ContactInfos', async () => {
        const nbButtonsBeforeCreate = await contactInfoComponentsPage.countDeleteButtons();

        await contactInfoComponentsPage.clickOnCreateButton();
        await contactInfoUpdatePage.setPrimaryPhoneInput('primaryPhone');
        expect(await contactInfoUpdatePage.getPrimaryPhoneInput()).to.eq('primaryPhone');
        await contactInfoUpdatePage.setSecondaryPhoneInput('secondaryPhone');
        expect(await contactInfoUpdatePage.getSecondaryPhoneInput()).to.eq('secondaryPhone');
        await contactInfoUpdatePage.setWorkPhoneInput('workPhone');
        expect(await contactInfoUpdatePage.getWorkPhoneInput()).to.eq('workPhone');
        await contactInfoUpdatePage.setWorkPhoneExtInput('workPhoneExt');
        expect(await contactInfoUpdatePage.getWorkPhoneExtInput()).to.eq('workPhoneExt');
        await contactInfoUpdatePage.setMobilePhoneInput('mobilePhone');
        expect(await contactInfoUpdatePage.getMobilePhoneInput()).to.eq('mobilePhone');
        await contactInfoUpdatePage.setSkypeIdInput('skypeId');
        expect(await contactInfoUpdatePage.getSkypeIdInput()).to.eq('skypeId');
        await contactInfoUpdatePage.userSelectLastOption();
        await contactInfoUpdatePage.save();
        expect(await contactInfoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ContactInfo', async () => {
        const nbButtonsBeforeDelete = await contactInfoComponentsPage.countDeleteButtons();
        await contactInfoComponentsPage.clickOnLastDeleteButton();

        contactInfoDeleteDialog = new ContactInfoDeleteDialog();
        expect(await contactInfoDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Contact Info?');
        await contactInfoDeleteDialog.clickOnConfirmButton();

        expect(await contactInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
