/**
 * 2014-08-01 K.OHWADA
 */ 

package jp.ohwada.android.usbdeviceinfo1;

import android.hardware.usb.UsbConstants;

/**
 * UsbDeviceInfo
 */	
public class UsbDeviceInfo {

	/**
	 * getClass
	 * @param int cls
	 * @return String
	 */
	public static String getClass( int cls ) {
		String str = "unknown class";
		switch ( cls ) {
			case UsbConstants.USB_CLASS_APP_SPEC:
				str = "Application specific class";
				break;
			case UsbConstants.USB_CLASS_AUDIO:
				str = "Audio devices class";
				break;
			case UsbConstants.USB_CLASS_CDC_DATA:
				str = "CDC communications device class";
				break;
			case UsbConstants.USB_CLASS_COMM:
				str = "Communication devices class";
				break;
			case UsbConstants.USB_CLASS_CONTENT_SEC:
				str = "Content security devices class";
				break;
			case UsbConstants.USB_CLASS_CSCID:
				str = "Content smart card devices class";
				break;
			case UsbConstants.USB_CLASS_HID:
				str = "HID human interface devices class";
				break;
			case UsbConstants.USB_CLASS_HUB:
				str = "HUB class";
				break;
			case UsbConstants.USB_CLASS_MASS_STORAGE:
				str = "Mass storage devices class";
				break;
			case UsbConstants.USB_CLASS_MISC:
				str = "Wireless miscellaneous devices class";
				break;
			case UsbConstants.USB_CLASS_PER_INTERFACE:
				str = "Per-interface basis class";
				break;
			case UsbConstants.USB_CLASS_PHYSICA:
				str = "Physical devices class";
				break;
			case UsbConstants.USB_CLASS_PRINTER:
				str = "Printers class";
				break;
			case UsbConstants.USB_CLASS_STILL_IMAGE:
				str = "Still image devices class";
				break;
			case UsbConstants.USB_CLASS_VENDOR_SPEC:
				str = "Vendor specific class";
				break;
			case UsbConstants.USB_CLASS_VIDEO:
				str = "Video devices class";
				break;
			case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
				str = "Wireless controller devices class";
				break;
		}
		return str;
	}

	/**
	 * getSubclass
	 * @param int cls
	 * @param int sub
	 * @return String
	 */
	public static  String getSubclass( int cls, int sub ) {
		String str = "unknown subclass";
		switch ( cls ) {
			case UsbConstants.USB_CLASS_APP_SPEC:
				break;
			case UsbConstants.USB_CLASS_AUDIO:
				str = getAudioSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_CDC_DATA:
				str = getCommDataSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_COMM:
				str = getCommSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_CONTENT_SEC:
				str = getSecuritySubclass( sub );
				break;
			case UsbConstants.USB_CLASS_CSCID:
				str = getCardSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_HID:
				str = getHidSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_HUB:
				break;
			case UsbConstants.USB_CLASS_MASS_STORAGE:
				str = getMassSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_MISC:
				break;
			case UsbConstants.USB_CLASS_PER_INTERFACE:
				break;
			case UsbConstants.USB_CLASS_PHYSICA:
				break;
			case UsbConstants.USB_CLASS_PRINTER:
				str = getPrinterSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_STILL_IMAGE:
				break;
			case UsbConstants.USB_CLASS_VENDOR_SPEC:
				break;
			case UsbConstants.USB_CLASS_VIDEO:
				str = getVideoSubclass( sub );
				break;
			case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
				str = getWirelessSubclass( sub );
				break;
		}
		return str;
	}

	/**
	 * getProtocol
	 * @param int cls
	 * @param int pro
	 * @return String
	 */
	public static  String getProtocol( int cls, int pro ) {
		String str = "unknown protocol";
		switch ( cls ) {
			case UsbConstants.USB_CLASS_APP_SPEC:
				break;
			case UsbConstants.USB_CLASS_AUDIO:
				str = getAudioProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_CDC_DATA:
				str = getCommDataProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_COMM:
				str = getCommProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_CONTENT_SEC:
				str = getSecurityProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_CSCID:
				str = getCardProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_HID:
				str = getHidProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_HUB:
				break;
			case UsbConstants.USB_CLASS_MASS_STORAGE:
				str = getMassProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_MISC:
				break;
			case UsbConstants.USB_CLASS_PER_INTERFACE:
				break;
			case UsbConstants.USB_CLASS_PHYSICA:
				break;
			case UsbConstants.USB_CLASS_PRINTER:
				str = getPrinterProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_STILL_IMAGE:
				break;
			case UsbConstants.USB_CLASS_VENDOR_SPEC:
				break;
			case UsbConstants.USB_CLASS_VIDEO:
				str = getVideoProtocol( pro );
				break;
			case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
				str = getWirelessProtocol( pro );
				break;
		}
		return str;
	}

	/**
	 * Audio Subclass
	 * Universal Serial Bus Device Class Definition for Audio Devices 1.0
	 * http://www.usb.org/developers/docs/devclass_docs/audio10.pdf
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getAudioSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "undefined subclass";
				break;
			case 0x01:
				str = "Audio Control";
				break;
			case 0x02:
				str = "Audio Streaming";
				break;
			case 0x03:
				str = "MIDI Streaming";							
				break;	
		}
		return str;
	}

	/**
	 * Audio Protocol
	 * @param int pro 
	 * @return String
	 */
	public static String getAudioProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "undefined protocol";
				break;
			case 0x20:
				str = "IP Ver2.0";
				break;
		}
		return str;
	}

	/**
	 * CDC Subclass
	 * Universal Serial Bus Class Definitions for Communications Devices 1.2
	 * http://www.usb.org/developers/docs/devclass_docs/CDC1.2_WMC1.1_012011.zip
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getCommSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "reserved subclass";
				break;
			case 0x01:
				str = "Direct Line Control Model";
				break;			
			case 0x02:
				str = "Abstract Control Model";
				break;	
			case 0x03:
				str = "Telephone Control Model";
				break;	
			case 0x04:
				str = "Multi-Channel Control Model";
				break;	
			case 0x05:
				str = "CAPI Control Model";
				break;									
			case 0x06:
				str = "Ethernet Networking Control Model";
				break;	
			case 0x07:
				str = "ATM Networking Control Model";
				break;	
			case 0x08:
				str = "Wireless Handset Control Model";
				break;	
			case 0x09:
				str = "Device Management";
				break;	
			case 0x0A:
				str = "Mobile Direct Line Model";
				break;									
			case 0x0B:
				str = "OBEX";
				break;	
			case 0x0C:
				str = "Ethernet Emulation Model";
				break;	
			case 0x0D:
				str = "Network Control Model";
				break;									
			case 0x0E:
				str = "Mobile Broadband Interface Model";
				break;				
			default:
				if (( sub >= 0x0F )&&( sub <= 0x7F )) {
					str = "reserved subclass (future use)";
				} else if (( sub >= 0x80 )&&( sub <= 0xFE )) {
					str = "vendor specific subclass";
				}	
				break;	
		}
		return str;
	}

	/**
	 * CDC Protocol
	 * @param int pro 
	 * @return String
	 */
	public static String getCommProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "No specific protocol";
				break;
			case 0x01:
				str = "AT Commands: V.250 etc";
				break;
			case 0x02:
				str = "AT Commands defined by PCCA-101";
				break;
			case 0x03:
				str = "AT Commands defined by PCCA-101 & Annex O";
				break;
			case 0x04:
				str = "AT Commands defined by GSM 07.07";
				break;
			case 0x05:
				str = "AT Commands defined by 3GPP 27.007";
				break;
			case 0x06:
				str = "AT Commands defined by TIA for CDMA";
				break;
			case 0x07:
				str = "Ethernet Emulation Model";
				break;
			case 0xFE:
				str = "External Protocol";
				break;
			case 0xFF:
				str = "Vendor specific protocol";
				break;
			default:
				if (( pro >= 0x08 )&&( pro <= 0xFD )) {
					str = "reserved protocol (future use)";
				}
				break;
		}
		return str;
	}

	/**
	 * CDC Data Subclass
	 * @param int sub 
	 * @return String
	 */
	public static String getCommDataSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "Data Interface";
				break;
		}
		return str;
	}

	/**
	 * CDC Data Protocol
	 * @param int pro 
	 * @return String
	 */
	public static String getCommDataProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "No specific protocol";
				break;
			case 0x01:
				str = "Network Transfer Block";
				break;
			case 0x30:
				str = "I.430 Physical interface protocol for ISDN BRI";
				break;
			case 0x31:
				str = "HDLC";
				break;
			case 0x32:
				str = "Transparent";
				break;
			case 0x50:
				str = "Management protocol for Q.921 data link protocol";
				break;
			case 0x51:
				str = "Data link protocol for Q.931";
				break;
			case 0x52:
				str = "TEI-multiplexor for Q.921 data link protocol";
				break;
			case 0x90:
				str = "V.42bis Data compression procedures";
				break;
			case 0x91:
				str = "Q.931 Euro-ISDN protocol control";
				break;
			case 0x92:
				str = "V.24 rate adaptation to ISDN";
				break;
			case 0x93:
				str = "CAPI Commands";
				break;
			case 0xFD:
				str = "Host based driver";
				break;
			case 0xFE:
				str = "The protocol(s) are described using a Protocol Unit Functional Descriptors on Communications Class Interface";
				break;
			case 0xFF:
				str = "Vendor specific protocol";
				break;				
			default:
				if ((( pro >= 0x02 )&&( pro <= 0x2F ))||
				    (( pro >= 0x33 )&&( pro <= 0x4F ))||
				    (( pro >= 0x53 )&&( pro <= 0x8F ))||
				    (( pro >= 0x94 )&&( pro <= 0xFC ))) {
					str = "reserved protocol (future use)";
				}
				break;
		}
		return str;
	}

	/**
	 * Content Security Subclass
	 * Universal Serial Bus Device Class Definition for Content Security Devices 2.0
	 * http://www.usb.org/developers/docs/devclass_docs/ContentSecurity_v2.0.pdf
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getSecuritySubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "default subclass";
				break;
		}
		return str;
	}

	/**
	 * Content Security Protocol
	 * @param int pro 
	 * @return String
	 */
	public static String getSecurityProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "default protocol";
				break;
		}
		return str;
	}

	/**
	 * Smart Card Subclass
	 * Universal Serial Bus Device Class: Smart Card CCID
	 * Specification for Integrated Circuit(s) Cards Interface Devices 1.1
	 * http://www.usb.org/developers/docs/devclass_docs/DWG_Smart-Card_CCID_Rev110.pdf
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getCardSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "default subclass";
				break;
		}
		return str;
	}

	/**
	 * Smart Card Protocol
	 * @param int pro 
	 * @return String
	 */
	public static String getCardProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "CCID Integrated Circuit Cards Interface Devices";
				break;
			case 0x01:
			case 0x02:
				str = "reserved protocol";
				break;
		}
		return str;
	}

	/**
	 * HID Subclass
	 * Device Class Definition for Human Interface Devices (HID) 1.11
	 * http://www.usb.org/developers/devclass_docs/HID1_11.pdf
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getHidSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "No Subclass";
				break;
			case 0x01:
				str = "Boot Interface";
				break;
		}
		return str;
	}

	/**
	 * HID Protocol
	 * @param int pro 
	 * @return String
	 */
	public static String getHidProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "None protocol";
				break;
			case 0x01:
				str = "Keyboard";
				break;
			case 0x02:
				str = "Mouse";
				break;
		}
		return str;
	}

	/**
	 * Mass Storage Subclass
	 * Universal Serial Bus Mass Storage Class Specification Overview 1.4
	 * http://www.usb.org/developers/docs/devclass_docs/Mass_Storage_Specification_Overview_v1.4_2-19-2010.pdf
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getMassSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "SCSI command set not reported";
				break;
			case 0x01:
				str = "RBC";
				break;
			case 0x02:
				str = "MMC-5 (ATAPI)";
				break;
			case 0x03:
				str = "QIC-157 Obsolete";
				break;
			case 0x04:
				str = "UFI Floppy Disk Drives";
				break;
			case 0x05:
				str = "SFF-8070i Obsolete";
				break;
			case 0x06:
				str = "SCSI transparent command set";
				break;
			case 0x07:
				str = "LSD FS";
				break;
			case 0x08:
				str = "IEEE 1667";
				break;
			case 0xFF:
				str = "Vendor specific subclass";
				break;				
			default:
				if (( sub >= 0x09 )&&( sub <= 0xFE )) {
					str = "reserved subclass";
				}
				break;
		}
		return str;
	}

	/**
	 * Mass Storage Protocol
	 * @param int pro
	 * @return String
	 */
	public static String getMassProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "CBI (with command completion interrupt)";
				break;
			case 0x01:
				str = "CBI (with no command completion interrupt)";
				break;
			case 0x02:
				str = "Obsolete";
				break;
			case 0x50:
				str = "BBB Bulk-Only";
				break;
			case 0x62:
				str = "UAS";
				break;
			case 0xFF:
				str = "Vendor specific protocol";
				break;
			default:
				if ((( pro >= 0x03 )&&( pro <= 0xEF )) ||
				   (( pro >= 0x51 )&&( pro <= 0x61 )) ||
				   (( pro >= 0x63 )&&( pro <= 0xFE ))) {
					str = "reserved protocol";
				}
				break;
		}
		return str;
	}

	/**
	 * Printer Subclass
	 * Universal Serial Bus Device Class Definition for Printing Devices) 1.1
	 * http://www.usb.org/developers/docs/devclass_docs/usbprint11a021811.pdf
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getPrinterSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x01:
				str = "Printers";
				break;
		}
		return str;
	}

	/**
	 * Printer Protocol
	 * @param int pro
	 * @return String
	 */
	public static String getPrinterProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "Reserved protocol";
				break;
			case 0x01:
				str = "Unidirectional interface";
				break;
			case 0x02:
				str = "Bi-directional interface";
				break;
			case 0x03:
				str = "1284.4 compatible bi-directional interface";
				break;
			case 0xFF:
				str = "Vendor specific protocol";
				break;
			default:
				if (( pro >= 0x04 )&&( pro <= 0xFE )) {
					str = "reserved protocol (future use)";
				}
				break;
		}
		return str;
	}

	/**
	 * Still Image Subclass
	 * Universal Serial Bus Still Image Capture Device Definition 1.0
	 * http://www.usb.org/developers/docs/devclass_docs/usb_still_img10.zip
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getImageSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x01:
				str = "Still Image Capture Device";
				break;
		}
		return str;
	}

	/**
	 * Still Image Protocol
	 * @param int pro
	 * @return String
	 */
	public static String getImageProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x01:
				str = "PIMA 15740 comp";
				break;
		}
		return str;
	}

	/**
	 * Video Subclass
	 * Universal Serial Bus Device Class Definition for Video Devices 1.5
	 * http://www.usb.org/developers/docs/devclass_docs/USB_Video_Class_1_5.zip
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getVideoSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x00:
				str = "undefined subclass";
				break;
			case 0x01:
				str = "Video Control";
				break;
			case 0x02:
				str = "Video Streaming";
				break;
			case 0x03:
				str = "Video Interface Collection";
				break;
		}
		return str;
	}

	/**
	 * Video Protocol
	 * @param int pro
	 * @return String
	 */
	public static String getVideoProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x00:
				str = "undefined protocol";
				break;
			case 0x01:
				str = "Protocol 15";
				break;
		}
		return str;
	}

	/**
	 * Wireless Subclass
	 * Wireless Universal Serial Bus Specification 1.1
	 * http://www.usb.org/developers/docs/wireless_documents/wusb1_1_20100910.zip
	 *
	 * @param int sub 
	 * @return String
	 */
	public static String getWirelessSubclass( int sub ) {
		String str = "unknown subclass";
		switch ( sub ) {
			case 0x01:
				str = "RF Controller";
				break;
			case 0x02:
				str = "Wireless USB Wire Adapter";
				break;
		}
		return str;
	}

	/**
	 * Wireless Protocol
	 * @param int pro
	 * @return String
	 */
	public static String getWirelessProtocol( int pro ) {
		String str = "unknown protocol";
		switch ( pro ) {
			case 0x01:
				str = "Host Wire Adapter Control";
				break;
			case 0x02:
				str = "UWB Radio Control";
				break;
			case 0x03:
				str = "Device Wire Adapter Transparent RPipe Interface";
				break;
		}
		return str;
	}
												
	/**
	 * getDirection
	 * @param int dir
	 * @return String
	 */
	public static String getDirection( int dir ) {
		String str = "unknown direction";
		switch ( dir ) {
			case UsbConstants.USB_DIR_IN:
				str = "IN - device to host";
				break;
			case UsbConstants.USB_DIR_OUT:
				str = "OUT - host to device";
				break;
		}
		return str;
	}			

	/**
	 * getType
	 * @param int type
	 * @return String
	 */
	public static String getType( int type ) {
		String str = "unknown type";
		switch ( type ) {
			case UsbConstants.USB_ENDPOINT_XFER_CONTROL:
				str = "Control type";
				break;
			case UsbConstants.USB_ENDPOINT_XFER_ISOC:
				str = "Isochronous type";
				break;
			case UsbConstants.USB_ENDPOINT_XFER_BULK:
				str = "Bulk type";
				break;
			case UsbConstants.USB_ENDPOINT_XFER_INT:
				str = "Interrupt type";
				break;	
		}
		return str;
	}
}
