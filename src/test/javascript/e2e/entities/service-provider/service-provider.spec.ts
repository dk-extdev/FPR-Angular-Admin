/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServiceProviderComponentsPage, ServiceProviderDeleteDialog, ServiceProviderUpdatePage } from './service-provider.page-object';

const expect = chai.expect;

describe('ServiceProvider e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let serviceProviderUpdatePage: ServiceProviderUpdatePage;
    let serviceProviderComponentsPage: ServiceProviderComponentsPage;
    /*let serviceProviderDeleteDialog: ServiceProviderDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ServiceProviders', async () => {
        await navBarPage.goToEntity('service-provider');
        serviceProviderComponentsPage = new ServiceProviderComponentsPage();
        expect(await serviceProviderComponentsPage.getTitle()).to.eq('Service Providers');
    });

    it('should load create ServiceProvider page', async () => {
        await serviceProviderComponentsPage.clickOnCreateButton();
        serviceProviderUpdatePage = new ServiceProviderUpdatePage();
        expect(await serviceProviderUpdatePage.getPageTitle()).to.eq('Create or edit a Service Provider');
        await serviceProviderUpdatePage.cancel();
    });

    /* it('should create and save ServiceProviders', async () => {
        const nbButtonsBeforeCreate = await serviceProviderComponentsPage.countDeleteButtons();

        await serviceProviderComponentsPage.clickOnCreateButton();
        await serviceProviderUpdatePage.setNameInput('name');
        expect(await serviceProviderUpdatePage.getNameInput()).to.eq('name');
        await serviceProviderUpdatePage.setDescriptionInput('description');
        expect(await serviceProviderUpdatePage.getDescriptionInput()).to.eq('description');
        await serviceProviderUpdatePage.setAddressInput('address');
        expect(await serviceProviderUpdatePage.getAddressInput()).to.eq('address');
        await serviceProviderUpdatePage.setStateInput('state');
        expect(await serviceProviderUpdatePage.getStateInput()).to.eq('state');
        await serviceProviderUpdatePage.setPostalCodeInput('postalCode');
        expect(await serviceProviderUpdatePage.getPostalCodeInput()).to.eq('postalCode');
        await serviceProviderUpdatePage.setCountryInput('country');
        expect(await serviceProviderUpdatePage.getCountryInput()).to.eq('country');
        await serviceProviderUpdatePage.setPhoneNumberInput('phoneNumber');
        expect(await serviceProviderUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        await serviceProviderUpdatePage.setPhoneExtInput('phoneExt');
        expect(await serviceProviderUpdatePage.getPhoneExtInput()).to.eq('phoneExt');
        await serviceProviderUpdatePage.setEmailInput('email');
        expect(await serviceProviderUpdatePage.getEmailInput()).to.eq('email');
        await serviceProviderUpdatePage.setWebsiteInput('website');
        expect(await serviceProviderUpdatePage.getWebsiteInput()).to.eq('website');
        await serviceProviderUpdatePage.setCurrencyInput('currency');
        expect(await serviceProviderUpdatePage.getCurrencyInput()).to.eq('currency');
        const selectedActive = serviceProviderUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await serviceProviderUpdatePage.getActiveInput().click();
            expect(await serviceProviderUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await serviceProviderUpdatePage.getActiveInput().click();
            expect(await serviceProviderUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await serviceProviderUpdatePage.setCityInput('city');
        expect(await serviceProviderUpdatePage.getCityInput()).to.eq('city');
        await serviceProviderUpdatePage.industrySelectLastOption();
        await serviceProviderUpdatePage.save();
        expect(await serviceProviderUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await serviceProviderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ServiceProvider', async () => {
        const nbButtonsBeforeDelete = await serviceProviderComponentsPage.countDeleteButtons();
        await serviceProviderComponentsPage.clickOnLastDeleteButton();

        serviceProviderDeleteDialog = new ServiceProviderDeleteDialog();
        expect(await serviceProviderDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Service Provider?');
        await serviceProviderDeleteDialog.clickOnConfirmButton();

        expect(await serviceProviderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
