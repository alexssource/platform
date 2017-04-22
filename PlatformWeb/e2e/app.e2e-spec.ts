import { PlatformWebPage } from "./app.po";

describe('platform-web App', function() {
    let page: PlatformWebPage;

    beforeEach(() => {
        page = new PlatformWebPage();
    });

    it('should display message saying app works', () => {
        page.navigateTo();
        expect(page.getParagraphText()).toEqual('app works!');
    });
});
