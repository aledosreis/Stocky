import RNFetchBlob from 'rn-fetch-blob';

const android = RNFetchBlob.android;
const update = (url,version) => {
  RNFetchBlob.config({
    addAndroidDownloads : {
      useDownloadManager : true,
      title: 'stocky.apk',
      mime : 'application/vnd.android.package-archive',
      mediaScannable : true,
      notification : true,
      path: `${RNFetchBlob.fs.dirs.DownloadDir}/stocky-v${version}.apk`,
    }
  })
  .fetch('GET', `${url}`)
  .then((res) => {
    android.actionViewIntent(res.path(), 'application/vnd.android.package-archive')
  });
}

  export default update;