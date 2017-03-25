package helloworld;

import org.apache.http.client.fluent.Request;

import com.amazonaws.util.json.JSONObject;

public class ApiClient {
	/**
	 * @return the block count of the Bitcoin Blockchain, Long.MIN_VALUE in case
	 *         of an error
	 */
	public static long fetchBlockCount() {
		long result = Long.MIN_VALUE;
		try {
			JSONObject responseJson = request("https://api.smartbit.com.au/v1/blockchain/totals");
			result = responseJson.getJSONObject("totals").getLong("block_count");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return the number of transactions in the latest block of the Bitcoin
	 *         Blockchain, Long.MIN_VALUE in case of an error
	 */
	public static long fetchTransactionCount() {
		long result = Long.MIN_VALUE;
		try {
			JSONObject responseJson = request("https://api.smartbit.com.au/v1/blockchain/blocks?limit=1");
			result = responseJson.getJSONArray("blocks").getJSONObject(0).getLong("transaction_count");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static JSONObject request(String url) throws Exception {
		return new JSONObject(Request.Get(url)
				.execute()
				.returnContent()
				.asString());
	}
}
