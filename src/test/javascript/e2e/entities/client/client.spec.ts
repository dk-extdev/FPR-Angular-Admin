/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClientComponentsPage, ClientDeleteDialog, ClientUpdatePage } from './client.page-object';

const expect = chai.expect;

describe('Client e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clientUpdatePage: ClientUpdatePage;
    let clientComponentsPage: ClientComponentsPage;
    /*let clientDeleteDialog: ClientDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Clients', async () => {
        await navBarPage.goToEntity('client');
        clientComponentsPage = new ClientComponentsPage();
        expect(await clientComponentsPage.getTitle()).to.eq('Clients');
    });

    it('should load create Client page', async () => {
        await clientComponentsPage.clickOnCreateButton();
        clientUpdatePage = new ClientUpdatePage();
        expect(await clientUpdatePage.getPageTitle()).to.eq('Create or edit a Client');
        await clientUpdatePage.cancel();
    });

    /* it('should create and save Clients', async () => {
        const nbButtonsBeforeCreate = await clientComponentsPage.countDeleteButtons();

        await clientComponentsPage.clickOnCreateButton();
        await clientUpdatePage.setNameInput('name');
        expect(await clientUpdatePage.getNameInput()).to.eq('name');
        await clientUpdatePage.setDescriptionInput('description');
        expect(await clientUpdatePage.getDescriptionInput()).to.eq('description');
        await clientUpdatePage.setAddressInput('address');
        expect(await clientUpdatePage.getAddressInput()).to.eq('address');
        await clientUpdatePage.setCityInput('city');
        expect(await clientUpdatePage.getCityInput()).to.eq('city');
        await clientUpdatePage.setStateInput('state');
        expect(await clientUpdatePage.getStateInput()).to.eq('state');
        await clientUpdatePage.setPostalCodeInput('postalCode');
        expect(await clientUpdatePage.getPostalCodeInput()).to.eq('postalCode');
        await clientUpdatePage.setCountryInput('country');
        expect(await clientUpdatePage.getCountryInput()).to.eq('country');
        await clientUpdatePage.setPhoneNumberInput('phoneNumber');
        expect(await clientUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        await clientUpdatePage.setPhoneExtInput('phoneExt');
        expect(await clientUpdatePage.getPhoneExtInput()).to.eq('phoneExt');
        await clientUpdatePage.setEmailInput('email');
        expect(await clientUpdatePage.getEmailInput()).to.eq('email');
        await clientUpdatePage.setWebsiteInput('website');
        expect(await clientUpdatePage.getWebsiteInput()).to.eq('website');
        await clientUpdatePage.setCurrencyInput('currency');
        expect(await clientUpdatePage.getCurrencyInput()).to.eq('currency');
        const selectedActive = clientUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await clientUpdatePage.getActiveInput().click();
            expect(await clientUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await clientUpdatePage.getActiveInput().click();
            expect(await clientUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await clientUpdatePage.industrySelectLastOption();
        await clientUpdatePage.save();
        expect(await clientUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await clientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Client', async () => {
        const nbButtonsBeforeDelete = await clientComponentsPage.countDeleteButtons();
        await clientComponentsPage.clickOnLastDeleteButton();

        clientDeleteDialog = new ClientDeleteDialog();
        expect(await clientDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Client?');
        await clientDeleteDialog.clickOnConfirmButton();

        expect(await clientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
