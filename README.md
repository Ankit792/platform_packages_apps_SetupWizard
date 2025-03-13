In order to implement the setup wizard functionality, the following two flags need to be set to 0. This configuration ensures the OS will execute the setup wizard on first boot or after a factory reset:
secure user_setup_complete 0
global device_provisioned 0

These default values can be controlled and modified from the defaults.xml file located in the following directory:  /android_build/device/variscite/imx8m/dart_mx8mm/ankit_service/overlay/frameworks/base/packages/SettingsProvider/res/values/defaults.xml

once the changes are done add the setup wizard in device.mk as PRODUCT_PACKSGES+=name.
Currently, the setup wizard only includes Wi-Fi setup, and some changes based on Android 13 security and requirements as original setup wizard is for older android versions.
