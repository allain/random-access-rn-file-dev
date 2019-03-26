
package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import java.util.Map;
import java.util.HashMap;

public class RNRandomAccessRnFileModule extends ReactContextBaseJavaModule {
  private static LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(); 
  private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 10, 5000, TimeUnit.MILLISECONDS, taskQueue);

  private final ReactApplicationContext reactContext;

  public RNRandomAccessRnFileModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    
  }

  @Override
  public String getName() {
    return "RNRandomAccessRnFile";
  }

  @ReactMethod
  public void ensureFileExists(final String filePath, final Promise promise) {
    threadPool.execute(new Runnable() {
      @Override
      public void run() {
        try {
          File f = new File(filePath);
          File parentDir = f.getParentFile();
          if (!parentDir.exists()) {
            parentDir.mkdirs();
          }

          if (!f.exists()) {
            f.createNewFile();
          }
        } catch (IOException ioe) {
          promise.reject("unable to create file: " + filePath);
        }
      }
    });
  }

  @ReactMethod void write(final String filePath, final String data, final int offset, final Promise promise) {
    threadPool.execute(new Runnable() {
      @Override
      public void run() {
        RandomAccessFile f = null;
        
        try {
          f = new RandomAccessFile(filePath, "rw");
          f.seek(offset);
          f.write(data.getBytes());
          promise.resolve(null);
        } catch (IOException ioe) {
          promise.reject("unable to write to file: " + filePath);
        } finally {
          if (f != null) {
            try {
              f.close();
            } catch (IOException ioe) {
              // Can't think of anything worth doing here
              System.err.println(ioe);
            }
          }
        }
      }
    });
  }
  
  @ReactMethod void read(final String filePath, final int size, final int offset, final Promise promise) {
    threadPool.execute(new Runnable() {
      @Override
      public void run() {
        RandomAccessFile f = null;
        
        try {
          f = new RandomAccessFile(filePath, "rw");
          final byte[] bytes = new byte[size];
          f.readFully(bytes);
          promise.resolve(new String(bytes, "utf8"));
        } catch (IOException ioe) {
          promise.reject("unable to write to file: " + filePath);
        } finally {
          if (f != null) {
            try {
              f.close();
            } catch (IOException ioe) {
              // Can't think of anything worth doing here
              System.err.println(ioe);
            }
          }
        }
      }
    });
  }
  
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("documentPath", this.getReactApplicationContext().getFilesDir().getAbsolutePath());
    constants.put("tempPath", this.getReactApplicationContext().getCacheDir().getAbsolutePath());
    constants.put("cachePath", this.getReactApplicationContext().getCacheDir().getAbsolutePath());
    return constants;
  }
  
}
