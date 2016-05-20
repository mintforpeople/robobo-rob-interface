package com.mytechia.robobo.rob.comm;

import com.mytechia.commons.framework.simplemessageprotocol.MessageDecoder;
import com.mytechia.commons.framework.simplemessageprotocol.exception.MessageFormatException;

public class InfraredConfigurationMessage extends RoboCommand {

	private static final String DATA_BYTE_HIGH = "dataByteHigh";

	private static final String DATA_BYTE_LOW = "dataByteLow";

	private static final String COMMAND_CODE = "commandCode";

	private static final String INFRARED_ID = "infraredId";

	private byte infraredId;
	
	private byte commandCode;
	
	private byte dataByteLow;
	
	private byte dataByteHigh;
	
	

	public InfraredConfigurationMessage(byte infraredId, byte commandCode, byte dataByteLow, byte dataByteHigh) {
		super();
		super.setCommandType(MessageType.InfraredConfigurationMessage.commandType);
		this.infraredId = infraredId;
		this.commandCode = commandCode;
		this.dataByteLow = dataByteLow;
		this.dataByteHigh = dataByteHigh;
	}

	public InfraredConfigurationMessage(byte[] message) throws MessageFormatException {
		super(message);
		super.setCommandType(MessageType.InfraredConfigurationMessage.commandType);
	}

	@Override
	protected int decodeMessageData(byte[] binaryMessage, int arg1) throws MessageFormatException {
		
		MessageDecoder decoder = this.getMessageDecoder();
		
		this.infraredId=decoder.readByte(INFRARED_ID);
		
		this.commandCode= decoder.readByte(COMMAND_CODE);
		
		this.dataByteLow= decoder.readByte(DATA_BYTE_LOW);
		
		this.dataByteHigh= decoder.readByte(DATA_BYTE_HIGH);
		
		return decoder.getArrayIndex();
	}

	public byte getInfraredId() {
		return infraredId;
	}

	public void setInfraredId(byte infraredId) {
		this.infraredId = infraredId;
	}

	public byte getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(byte commandCode) {
		this.commandCode = commandCode;
	}

	public byte getDataByteLow() {
		return dataByteLow;
	}

	public void setDataByteLow(byte dataByteLow) {
		this.dataByteLow = dataByteLow;
	}

	public byte getDataByteHigh() {
		return dataByteHigh;
	}

	public void setDataByteHigh(byte dataByteHigh) {
		this.dataByteHigh = dataByteHigh;
	}
	
	

}
