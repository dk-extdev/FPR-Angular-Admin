/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MerchantComponentsPage, MerchantDeleteDialog, MerchantUpdatePage } from './merchant.page-object';

const expect = chai.expect;

describe('Merchant e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let merchantUpdatePage: MerchantUpdatePage;
    let merchantComponentsPage: MerchantComponentsPage;
    /*let merchantDeleteDialog: MerchantDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Merchants', async () => {
        await navBarPage.goToEntity('merchant');
        merchantComponentsPage = new MerchantComponentsPage();
        expect(await merchantComponentsPage.getTitle()).to.eq('Merchants');
    });

    it('should load create Merchant page', async () => {
        await merchantComponentsPage.clickOnCreateButton();
        merchantUpdatePage = new MerchantUpdatePage();
        expect(await merchantUpdatePage.getPageTitle()).to.eq('Create or edit a Merchant');
        await merchantUpdatePage.cancel();
    });

    /* it('should create and save Merchants', async () => {
        const nbButtonsBeforeCreate = await merchantComponentsPage.countDeleteButtons();

        await merchantComponentsPage.clickOnCreateButton();
        await merchantUpdatePage.setNameInput('name');
        expect(await merchantUpdatePage.getNameInput()).to.eq('name');
        await merchantUpdatePage.setDescriptionInput('description');
        expect(await merchantUpdatePage.getDescriptionInput()).to.eq('description');
        await merchantUpdatePage.setAddressInput('address');
        expect(await merchantUpdatePage.getAddressInput()).to.eq('address');
        await merchantUpdatePage.setCityInput('city');
        expect(await merchantUpdatePage.getCityInput()).to.eq('city');
        await merchantUpdatePage.setStateInput('state');
        expect(await merchantUpdatePage.getStateInput()).to.eq('state');
        await merchantUpdatePage.setPostalCodeInput('postalCode');
        expect(await merchantUpdatePage.getPostalCodeInput()).to.eq('postalCode');
        await merchantUpdatePage.setCountryInput('country');
        expect(await merchantUpdatePage.getCountryInput()).to.eq('country');
        await merchantUpdatePage.setPhoneNumberInput('phoneNumber');
        expect(await merchantUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        await merchantUpdatePage.setPhoneExtInput('phoneExt');
        expect(await merchantUpdatePage.getPhoneExtInput()).to.eq('phoneExt');
        await merchantUpdatePage.setEmailInput('email');
        expect(await merchantUpdatePage.getEmailInput()).to.eq('email');
        await merchantUpdatePage.setWebsiteInput('website');
        expect(await merchantUpdatePage.getWebsiteInput()).to.eq('website');
        await merchantUpdatePage.setCurrencyInput('currency');
        expect(await merchantUpdatePage.getCurrencyInput()).to.eq('currency');
        const selectedActive = merchantUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await merchantUpdatePage.getActiveInput().click();
            expect(await merchantUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await merchantUpdatePage.getActiveInput().click();
            expect(await merchantUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await merchantUpdatePage.industrySelectLastOption();
        await merchantUpdatePage.clientSelectLastOption();
        await merchantUpdatePage.save();
        expect(await merchantUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await merchantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Merchant', async () => {
        const nbButtonsBeforeDelete = await merchantComponentsPage.countDeleteButtons();
        await merchantComponentsPage.clickOnLastDeleteButton();

        merchantDeleteDialog = new MerchantDeleteDialog();
        expect(await merchantDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Merchant?');
        await merchantDeleteDialog.clickOnConfirmButton();

        expect(await merchantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
