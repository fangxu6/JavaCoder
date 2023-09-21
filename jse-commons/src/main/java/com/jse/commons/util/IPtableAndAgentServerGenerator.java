package com.jse.commons.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IPtableAndAgentServerGenerator {

	private static final String	Trade_server_SQL	= "INSERT INTO stock.AgentServer(agentServerHost, agentServerPort, agentType, machineType, channelId, networkMacAddress, vncPort, vncUsername, vncPassword, remoteConnectionHost, remoteConnectionPort, remoteConnectionUsername, remoteConnectionPassword, hardDiskSerialNumber, warInfo, dllInfo, exeInfo, securityId, createTime, updateTime, deleteTime, reserve0, reserve1, reserve2, hostServerHost, isEnabled, releaseStatus, releaseTime, vmName, agentGroupType, agentGroupId, autoLoginConfig) VALUES ('#InterNalIp#', 8080, 2, NULL, 9, '', #VNEPort#, '', 'zqvnc123!', '#gateWayIp#', #remotePort#, 'administrator', 'zqvnc123!', '', '', '', NULL, NULL, 1447147196000, 1511354488265, NULL, NULL, NULL, NULL, 'thsSimulateAgent', 0, 0, NULL, '#VMName#', 1, #AgentGroupIp#, NULL);\r\n";
	private static final String	QUERY_server_SQL	= "INSERT INTO stock.AgentServer(agentServerHost, agentServerPort, agentType, machineType, channelId, networkMacAddress, vncPort, vncUsername, vncPassword, remoteConnectionHost, remoteConnectionPort, remoteConnectionUsername, remoteConnectionPassword, hardDiskSerialNumber, warInfo, dllInfo, exeInfo, securityId, createTime, updateTime, deleteTime, reserve0, reserve1, reserve2, hostServerHost, isEnabled, releaseStatus, releaseTime, vmName, agentGroupType, agentGroupId, autoLoginConfig) VALUES ('#InterNalIp#', 8080, 2, NULL, 9, '', #VNEPort#, '', 'zqvnc123!', '#gateWayIp#', #remotePort#, 'administrator', 'zqvnc123!', '', '', '', NULL, NULL, 1447147196000, 1511354488265, NULL, NULL, NULL, NULL, 'thsSimulateAgent', 0, 0, NULL, '#VMName#', 0, #AgentGroupIp#, NULL);\r\n";
	private static final String	VNC_IPTABLE			= "-A PREROUTING -i eth0 -p tcp -m tcp -s 47.99.194.100 --sport 1024:65535 --dport #PORT# -j DNAT --to-destination #InterNalIp#:5900\r\n";
	private static final String	REMOVE_IPTABLE		= "-A PREROUTING -i eth0 -p tcp -m tcp -s 47.99.194.100 --sport 1024:65535 --dport #PORT# -j DNAT --to-destination #InterNalIp#:3389\r\n";

	/**
	 * 当前iptable 中最后一个port
	 */
	private static int			lastestIptablePort	= 7570;
	/**
	 * agentGroupId 最新id+1
	 */
	private static int			nextAgentGroupId	= 2000006;
	/**
	 * 网关ip
	 */
	private static final String	gateWayIp			= "139.224.134.81";

	private static final String	filepath			= "C:\\work\\learn\\demo\\ecs_instance_list_cn-shanghai_2019-07-08.csv";

	public static void main(String[] args) {
		StringBuffer iptables = new StringBuffer();
		StringBuffer agentServerSql = new StringBuffer();
		try {

			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(filepath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				String line = "";
				String everyLine = "";
				int lineNumber = 1;
				Map<String, Integer> map = new HashMap<String, Integer>();
				int index = 0;
				while ((line = br.readLine()) != null) {
					everyLine = line;
					String[] values = everyLine.split(",");
					if (lineNumber == 1) {
						initMap(map, values);
						lineNumber++;
						continue;
					}

					String vmName = values[map.get(VMName)].replace("\"", "").trim();
					String internalIp = values[map.get(InterNalIp)].replace("\"", "").trim();
					int vncPort = (++lastestIptablePort);
					int removePort = (++lastestIptablePort);
					iptables.append(VNC_IPTABLE.replace("#PORT#", "" + vncPort).replace("#InterNalIp#", internalIp));
					iptables.append(REMOVE_IPTABLE.replace("#PORT#", "" + removePort).replace("#InterNalIp#",
							internalIp));
					if (index % 2 == 0) {
						agentServerSql.append(Trade_server_SQL.replace("#InterNalIp#", internalIp)
								.replace("#gateWayIp#", gateWayIp).replace("#VNEPort#", "" + vncPort)
								.replace("#remotePort#", "" + removePort).replace("#VMName#", vmName)
								.replace("#AgentGroupIp#", "" + nextAgentGroupId));
					}
					if (index % 2 == 1) {
						agentServerSql.append(QUERY_server_SQL.replace("#InterNalIp#", internalIp)
								.replace("#gateWayIp#", gateWayIp).replace("#VNEPort#", "" + vncPort)
								.replace("#remotePort#", "" + removePort).replace("#VMName#", vmName)
								.replace("#AgentGroupIp#", "" + nextAgentGroupId));
						nextAgentGroupId++;
					}
					index++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(iptables);

		System.out.println();
		System.out.println(agentServerSql);
	}

	private static final String	VMName		= "实例ID";
	private static final String	InterNalIp	= "内网IP";

	private static void initMap(Map<String, Integer> map, String[] values) {
		for (int i = 0; i < values.length; i++) {
			String v = values[i].replace("\"", "");
			if (v.contains(VMName)) {
				map.put(VMName, i);
			} else if (v.contains(InterNalIp)) {
				map.put(InterNalIp, i);
			}
			if (map.size() == 2) {
				break;
			}
		}
	}
}
