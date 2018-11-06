/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartnerComponentsPage, PartnerDeleteDialog, PartnerUpdatePage } from './partner.page-object';

const expect = chai.expect;

describe('Partner e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let partnerUpdatePage: PartnerUpdatePage;
    let partnerComponentsPage: PartnerComponentsPage;
    /*let partnerDeleteDialog: PartnerDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Partners', async () => {
        await navBarPage.goToEntity('partner');
        partnerComponentsPage = new PartnerComponentsPage();
        expect(await partnerComponentsPage.getTitle()).to.eq('Partners');
    });

    it('should load create Partner page', async () => {
        await partnerComponentsPage.clickOnCreateButton();
        partnerUpdatePage = new PartnerUpdatePage();
        expect(await partnerUpdatePage.getPageTitle()).to.eq('Create or edit a Partner');
        await partnerUpdatePage.cancel();
    });

    /* it('should create and save Partners', async () => {
        const nbButtonsBeforeCreate = await partnerComponentsPage.countDeleteButtons();

        await partnerComponentsPage.clickOnCreateButton();
        await partnerUpdatePage.setNameInput('name');
        expect(await partnerUpdatePage.getNameInput()).to.eq('name');
        await partnerUpdatePage.setDescriptionInput('description');
        expect(await partnerUpdatePage.getDescriptionInput()).to.eq('description');
        await partnerUpdatePage.setAddressInput('address');
        expect(await partnerUpdatePage.getAddressInput()).to.eq('address');
        await partnerUpdatePage.setCityInput('city');
        expect(await partnerUpdatePage.getCityInput()).to.eq('city');
        await partnerUpdatePage.setStateInput('state');
        expect(await partnerUpdatePage.getStateInput()).to.eq('state');
        await partnerUpdatePage.setPostalCodeInput('postalCode');
        expect(await partnerUpdatePage.getPostalCodeInput()).to.eq('postalCode');
        await partnerUpdatePage.setCountryInput('country');
        expect(await partnerUpdatePage.getCountryInput()).to.eq('country');
        await partnerUpdatePage.setPhoneNumberInput('phoneNumber');
        expect(await partnerUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        await partnerUpdatePage.setPhoneExtInput('phoneExt');
        expect(await partnerUpdatePage.getPhoneExtInput()).to.eq('phoneExt');
        await partnerUpdatePage.setEmailInput('email');
        expect(await partnerUpdatePage.getEmailInput()).to.eq('email');
        await partnerUpdatePage.setWebsiteInput('website');
        expect(await partnerUpdatePage.getWebsiteInput()).to.eq('website');
        await partnerUpdatePage.setCurrencyInput('currency');
        expect(await partnerUpdatePage.getCurrencyInput()).to.eq('currency');
        const selectedActive = partnerUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await partnerUpdatePage.getActiveInput().click();
            expect(await partnerUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await partnerUpdatePage.getActiveInput().click();
            expect(await partnerUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await partnerUpdatePage.typeSelectLastOption();
        await partnerUpdatePage.industrySelectLastOption();
        await partnerUpdatePage.save();
        expect(await partnerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await partnerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Partner', async () => {
        const nbButtonsBeforeDelete = await partnerComponentsPage.countDeleteButtons();
        await partnerComponentsPage.clickOnLastDeleteButton();

        partnerDeleteDialog = new PartnerDeleteDialog();
        expect(await partnerDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Partner?');
        await partnerDeleteDialog.clickOnConfirmButton();

        expect(await partnerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
