/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartnerUserComponentsPage, PartnerUserDeleteDialog, PartnerUserUpdatePage } from './partner-user.page-object';

const expect = chai.expect;

describe('PartnerUser e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let partnerUserUpdatePage: PartnerUserUpdatePage;
    let partnerUserComponentsPage: PartnerUserComponentsPage;
    /*let partnerUserDeleteDialog: PartnerUserDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PartnerUsers', async () => {
        await navBarPage.goToEntity('partner-user');
        partnerUserComponentsPage = new PartnerUserComponentsPage();
        expect(await partnerUserComponentsPage.getTitle()).to.eq('Partner Users');
    });

    it('should load create PartnerUser page', async () => {
        await partnerUserComponentsPage.clickOnCreateButton();
        partnerUserUpdatePage = new PartnerUserUpdatePage();
        expect(await partnerUserUpdatePage.getPageTitle()).to.eq('Create or edit a Partner User');
        await partnerUserUpdatePage.cancel();
    });

    /* it('should create and save PartnerUsers', async () => {
        const nbButtonsBeforeCreate = await partnerUserComponentsPage.countDeleteButtons();

        await partnerUserComponentsPage.clickOnCreateButton();
        await partnerUserUpdatePage.partnerSelectLastOption();
        await partnerUserUpdatePage.userSelectLastOption();
        await partnerUserUpdatePage.save();
        expect(await partnerUserUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await partnerUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last PartnerUser', async () => {
        const nbButtonsBeforeDelete = await partnerUserComponentsPage.countDeleteButtons();
        await partnerUserComponentsPage.clickOnLastDeleteButton();

        partnerUserDeleteDialog = new PartnerUserDeleteDialog();
        expect(await partnerUserDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Partner User?');
        await partnerUserDeleteDialog.clickOnConfirmButton();

        expect(await partnerUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
