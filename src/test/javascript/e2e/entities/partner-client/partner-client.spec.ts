/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartnerClientComponentsPage, PartnerClientDeleteDialog, PartnerClientUpdatePage } from './partner-client.page-object';

const expect = chai.expect;

describe('PartnerClient e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let partnerClientUpdatePage: PartnerClientUpdatePage;
    let partnerClientComponentsPage: PartnerClientComponentsPage;
    /*let partnerClientDeleteDialog: PartnerClientDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PartnerClients', async () => {
        await navBarPage.goToEntity('partner-client');
        partnerClientComponentsPage = new PartnerClientComponentsPage();
        expect(await partnerClientComponentsPage.getTitle()).to.eq('Partner Clients');
    });

    it('should load create PartnerClient page', async () => {
        await partnerClientComponentsPage.clickOnCreateButton();
        partnerClientUpdatePage = new PartnerClientUpdatePage();
        expect(await partnerClientUpdatePage.getPageTitle()).to.eq('Create or edit a Partner Client');
        await partnerClientUpdatePage.cancel();
    });

    /* it('should create and save PartnerClients', async () => {
        const nbButtonsBeforeCreate = await partnerClientComponentsPage.countDeleteButtons();

        await partnerClientComponentsPage.clickOnCreateButton();
        await partnerClientUpdatePage.partnerSelectLastOption();
        await partnerClientUpdatePage.merchantAccountSelectLastOption();
        await partnerClientUpdatePage.save();
        expect(await partnerClientUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await partnerClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last PartnerClient', async () => {
        const nbButtonsBeforeDelete = await partnerClientComponentsPage.countDeleteButtons();
        await partnerClientComponentsPage.clickOnLastDeleteButton();

        partnerClientDeleteDialog = new PartnerClientDeleteDialog();
        expect(await partnerClientDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Partner Client?');
        await partnerClientDeleteDialog.clickOnConfirmButton();

        expect(await partnerClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
