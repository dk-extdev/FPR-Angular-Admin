/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClientUserComponentsPage, ClientUserDeleteDialog, ClientUserUpdatePage } from './client-user.page-object';

const expect = chai.expect;

describe('ClientUser e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clientUserUpdatePage: ClientUserUpdatePage;
    let clientUserComponentsPage: ClientUserComponentsPage;
    /*let clientUserDeleteDialog: ClientUserDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ClientUsers', async () => {
        await navBarPage.goToEntity('client-user');
        clientUserComponentsPage = new ClientUserComponentsPage();
        expect(await clientUserComponentsPage.getTitle()).to.eq('Client Users');
    });

    it('should load create ClientUser page', async () => {
        await clientUserComponentsPage.clickOnCreateButton();
        clientUserUpdatePage = new ClientUserUpdatePage();
        expect(await clientUserUpdatePage.getPageTitle()).to.eq('Create or edit a Client User');
        await clientUserUpdatePage.cancel();
    });

    /* it('should create and save ClientUsers', async () => {
        const nbButtonsBeforeCreate = await clientUserComponentsPage.countDeleteButtons();

        await clientUserComponentsPage.clickOnCreateButton();
        await clientUserUpdatePage.clientSelectLastOption();
        await clientUserUpdatePage.userSelectLastOption();
        await clientUserUpdatePage.save();
        expect(await clientUserUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await clientUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ClientUser', async () => {
        const nbButtonsBeforeDelete = await clientUserComponentsPage.countDeleteButtons();
        await clientUserComponentsPage.clickOnLastDeleteButton();

        clientUserDeleteDialog = new ClientUserDeleteDialog();
        expect(await clientUserDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Client User?');
        await clientUserDeleteDialog.clickOnConfirmButton();

        expect(await clientUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
