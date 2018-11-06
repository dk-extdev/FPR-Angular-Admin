/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MerchantAccountComponentsPage, MerchantAccountDeleteDialog, MerchantAccountUpdatePage } from './merchant-account.page-object';

const expect = chai.expect;

describe('MerchantAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let merchantAccountUpdatePage: MerchantAccountUpdatePage;
    let merchantAccountComponentsPage: MerchantAccountComponentsPage;
    /*let merchantAccountDeleteDialog: MerchantAccountDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load MerchantAccounts', async () => {
        await navBarPage.goToEntity('merchant-account');
        merchantAccountComponentsPage = new MerchantAccountComponentsPage();
        expect(await merchantAccountComponentsPage.getTitle()).to.eq('Merchant Accounts');
    });

    it('should load create MerchantAccount page', async () => {
        await merchantAccountComponentsPage.clickOnCreateButton();
        merchantAccountUpdatePage = new MerchantAccountUpdatePage();
        expect(await merchantAccountUpdatePage.getPageTitle()).to.eq('Create or edit a Merchant Account');
        await merchantAccountUpdatePage.cancel();
    });

    /* it('should create and save MerchantAccounts', async () => {
        const nbButtonsBeforeCreate = await merchantAccountComponentsPage.countDeleteButtons();

        await merchantAccountComponentsPage.clickOnCreateButton();
        await merchantAccountUpdatePage.setMidInput('mid');
        expect(await merchantAccountUpdatePage.getMidInput()).to.eq('mid');
        await merchantAccountUpdatePage.setMidDescriptorInput('midDescriptor');
        expect(await merchantAccountUpdatePage.getMidDescriptorInput()).to.eq('midDescriptor');
        const selectedActive = merchantAccountUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await merchantAccountUpdatePage.getActiveInput().click();
            expect(await merchantAccountUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await merchantAccountUpdatePage.getActiveInput().click();
            expect(await merchantAccountUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await merchantAccountUpdatePage.merchantSelectLastOption();
        await merchantAccountUpdatePage.clientCrmSelectLastOption();
        await merchantAccountUpdatePage.save();
        expect(await merchantAccountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await merchantAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last MerchantAccount', async () => {
        const nbButtonsBeforeDelete = await merchantAccountComponentsPage.countDeleteButtons();
        await merchantAccountComponentsPage.clickOnLastDeleteButton();

        merchantAccountDeleteDialog = new MerchantAccountDeleteDialog();
        expect(await merchantAccountDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Merchant Account?');
        await merchantAccountDeleteDialog.clickOnConfirmButton();

        expect(await merchantAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
