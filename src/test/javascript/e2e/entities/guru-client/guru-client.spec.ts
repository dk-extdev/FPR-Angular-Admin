/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GuruClientComponentsPage, GuruClientDeleteDialog, GuruClientUpdatePage } from './guru-client.page-object';

const expect = chai.expect;

describe('GuruClient e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let guruClientUpdatePage: GuruClientUpdatePage;
    let guruClientComponentsPage: GuruClientComponentsPage;
    /*let guruClientDeleteDialog: GuruClientDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load GuruClients', async () => {
        await navBarPage.goToEntity('guru-client');
        guruClientComponentsPage = new GuruClientComponentsPage();
        expect(await guruClientComponentsPage.getTitle()).to.eq('Guru Clients');
    });

    it('should load create GuruClient page', async () => {
        await guruClientComponentsPage.clickOnCreateButton();
        guruClientUpdatePage = new GuruClientUpdatePage();
        expect(await guruClientUpdatePage.getPageTitle()).to.eq('Create or edit a Guru Client');
        await guruClientUpdatePage.cancel();
    });

    /* it('should create and save GuruClients', async () => {
        const nbButtonsBeforeCreate = await guruClientComponentsPage.countDeleteButtons();

        await guruClientComponentsPage.clickOnCreateButton();
        await guruClientUpdatePage.levelSelectLastOption();
        await guruClientUpdatePage.guruSelectLastOption();
        await guruClientUpdatePage.clientSelectLastOption();
        await guruClientUpdatePage.save();
        expect(await guruClientUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await guruClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last GuruClient', async () => {
        const nbButtonsBeforeDelete = await guruClientComponentsPage.countDeleteButtons();
        await guruClientComponentsPage.clickOnLastDeleteButton();

        guruClientDeleteDialog = new GuruClientDeleteDialog();
        expect(await guruClientDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Guru Client?');
        await guruClientDeleteDialog.clickOnConfirmButton();

        expect(await guruClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
