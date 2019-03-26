
# react-native-random-access-rn-file

## Getting started

`$ npm install react-native-random-access-rn-file --save`

### Mostly automatic installation

`$ react-native link react-native-random-access-rn-file`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-random-access-rn-file` and add `RNRandomAccessRnFile.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRandomAccessRnFile.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNRandomAccessRnFilePackage;` to the imports at the top of the file
  - Add `new RNRandomAccessRnFilePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-random-access-rn-file'
  	project(':react-native-random-access-rn-file').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-random-access-rn-file/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-random-access-rn-file')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNRandomAccessRnFile.sln` in `node_modules/react-native-random-access-rn-file/windows/RNRandomAccessRnFile.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Random.Access.Rn.File.RNRandomAccessRnFile;` to the usings at the top of the file
  - Add `new RNRandomAccessRnFilePackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNRandomAccessRnFile from 'react-native-random-access-rn-file';

// TODO: What to do with the module?
RNRandomAccessRnFile;
```
  