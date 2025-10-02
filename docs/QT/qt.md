https://doc.qt.io/
https://my.qt.io/download
https://doc.qt.io/qt-6/gettingstarted.html

https://doc.qt.io/qt-6/create-your-first-applications.html


https://doc.qt.io/qt-6/qtdesigner-manual.html

labview: https://lv.qizhen.xyz/en/oop_generic



## build from source
https://doc.qt.io/qt-6/getting-sources-from-git.html
https://doc.qt.io/qt-6/build-sources.html
https://wiki.qt.io/Building_Qt_6_from_Git#Getting_the_source_code


## get code
```
git clone https://code.qt.io/qt/qt5.git qt
cd qt
git switch 6.9.1
```
> Do not worry about the name mentioning qt5.git. This is also used for Qt 6.




../configure -prefix /usr/local/Qt/6.2.1 -I$OPENSSL_ROOT_DIR/include -L$OPENSSL_ROOT_DIR/lib -opensource -confirm-license  -nomake examples -nomake tests -skip qt3d -skip qtquick3d

### 
Object Model： https://doc.qt.io/qt-6/object.html
The Event System ： https://doc.qt.io/qt-6/eventsandfilters.html
Layout Management： https://doc.qt.io/qt-6/layout.html