/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.philippgille.alexa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

/**
 * This sample shows how to create a simple speechlet for handling speechlet requests.
 */
public class BlockchainExplorerSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(BlockchainExplorerSpeechlet.class);

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("BlockCountIntent".equals(intentName)) {
        	return getBlockCountResponse();
        } else if ("TransactionCountIntent".equals(intentName)) {
        	return getTransactionCountResponse();
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Willkommen beim Blockchain Explorer Alexa Skill." +
        		" Du kannst mich fragen wie lang die Bitcoin Blockchain ist" +
        		" oder wie viele Transaktionen im letzten Block der Bitcoin Blockchain waren.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Willkommen");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

	/**
	 * Creates a {@code SpeechletResponse} for the block count intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getBlockCountResponse() {
		// Fetch the current block count from the API
		long blockCount = ApiClient.fetchBlockCount();
		
		String speechText = "Es ist ein Fehler beim Abfragen der Blockanzahl aufgetreten.";    	
		// If anything failed, blockCount is Long.MIN_VALUE. So set a response only if that's *not* the case.
		if (blockCount != Long.MIN_VALUE)
		{
			speechText = "Die aktuelle Blockanzahl ist " + blockCount;
		}
	
	    // Create the Simple card content.
	    SimpleCard card = new SimpleCard();
	    card.setTitle("Länge der Bitcoin Blockchain");
	    card.setContent(speechText);
	
	    // Create the plain text output.
	    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	    speech.setText(speechText);
	
	    return SpeechletResponse.newTellResponse(speech, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the transaction count intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getTransactionCountResponse() {
		// Fetch the transaction count of the latest block from the API
		long transactionCount = ApiClient.fetchTransactionCount();
		
		String speechText = "Es ist ein Fehler beim Abfragen der Transaktionsanzahl aufgetreten.";    	
		// If anything failed, transactionCount is Long.MIN_VALUE. So set a response only if that's *not* the case.
		if (transactionCount != Long.MIN_VALUE)
		{
			speechText = "Die Anzahl der Transaktionen im letzten Block ist " + transactionCount;
		}
	
	    // Create the Simple card content.
	    SimpleCard card = new SimpleCard();
	    card.setTitle("Transaktionsanzahl im letzten Block der Bitcoin Blockchain");
	    card.setContent(speechText);
	
	    // Create the plain text output.
	    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	    speech.setText(speechText);
	
	    return SpeechletResponse.newTellResponse(speech, card);
	}

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "Du kannst mich fragen wie lang die Bitcoin Blockchain ist" +
        		" oder wie viele Transaktionen im letzten Block der Bitcoin Blockchain waren.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Hilfe");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}
