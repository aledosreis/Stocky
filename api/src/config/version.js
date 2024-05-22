module.exports = {
    async update(request, response) {
        const versionCheck = {
            version: 3.1,
            url: 'https://drive.google.com/u/0/uc?id=182ECLrxKk92jxm7hS4XCuK3Ic2r56pWQ&export=download'
          }
        return response.status(200).json(versionCheck);
    }
}