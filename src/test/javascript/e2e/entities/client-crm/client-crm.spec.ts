/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClientCrmComponentsPage, ClientCrmDeleteDialog, ClientCrmUpdatePage } from './client-crm.page-object';

const expect = chai.expect;

describe('ClientCrm e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clientCrmUpdatePage: ClientCrmUpdatePage;
    let clientCrmComponentsPage: ClientCrmComponentsPage;
    /*let clientCrmDeleteDialog: ClientCrmDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ClientCrms', async () => {
        await navBarPage.goToEntity('client-crm');
        clientCrmComponentsPage = new ClientCrmComponentsPage();
        expect(await clientCrmComponentsPage.getTitle()).to.eq('Client Crms');
    });

    it('should load create ClientCrm page', async () => {
        await clientCrmComponentsPage.clickOnCreateButton();
        clientCrmUpdatePage = new ClientCrmUpdatePage();
        expect(await clientCrmUpdatePage.getPageTitle()).to.eq('Create or edit a Client Crm');
        await clientCrmUpdatePage.cancel();
    });

    /* it('should create and save ClientCrms', async () => {
        const nbButtonsBeforeCreate = await clientCrmComponentsPage.countDeleteButtons();

        await clientCrmComponentsPage.clickOnCreateButton();
        await clientCrmUpdatePage.setEndpointInput('endpoint');
        expect(await clientCrmUpdatePage.getEndpointInput()).to.eq('endpoint');
        await clientCrmUpdatePage.setWebUsernameInput('webUsername');
        expect(await clientCrmUpdatePage.getWebUsernameInput()).to.eq('webUsername');
        await clientCrmUpdatePage.setWebPasswordInput('webPassword');
        expect(await clientCrmUpdatePage.getWebPasswordInput()).to.eq('webPassword');
        await clientCrmUpdatePage.setApiKeyInput('apiKey');
        expect(await clientCrmUpdatePage.getApiKeyInput()).to.eq('apiKey');
        await clientCrmUpdatePage.setApiSecretInput('apiSecret');
        expect(await clientCrmUpdatePage.getApiSecretInput()).to.eq('apiSecret');
        await clientCrmUpdatePage.clientSelectLastOption();
        await clientCrmUpdatePage.vendorSelectLastOption();
        await clientCrmUpdatePage.save();
        expect(await clientCrmUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await clientCrmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ClientCrm', async () => {
        const nbButtonsBeforeDelete = await clientCrmComponentsPage.countDeleteButtons();
        await clientCrmComponentsPage.clickOnLastDeleteButton();

        clientCrmDeleteDialog = new ClientCrmDeleteDialog();
        expect(await clientCrmDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Client Crm?');
        await clientCrmDeleteDialog.clickOnConfirmButton();

        expect(await clientCrmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
