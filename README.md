AWS Lambda function for the Alexa skill "Blockchain Explorer"
==========================================================

A simple AWS Lambda function for an Alexa skill to ask about Blockchain data using the Alexa SDK.

Usage Examples
-------------

### One-shot model:

```
User: "Alexa, frage Block Explorer: Was ist eine Blockchain?"
Alexa: "Eine Blockchain ist eine verteilte Datenbank, welche aus aneinandergereihten sogenannten Blöcken besteht. Jeder Block [...]"

User: "Alexa, frage Block Explorer: Was ist Bitcoin?"
Alexa: "Bitcoin ist eine dezentrale digitale Kryptowährung, die von Satoshi Nakamoto erfunden und [...]"

User: "Alexa, frage Block Explorer: Wie lang ist die Bitcoin Blockchain?"
Alexa: "Die aktuelle Blockanzahl ist 458866"

User: "Alexa, frage Block Explorer: Wie viele Transaktionen waren im letzten Block der Bitcoin Blockchain?"
Alexa: "Die Anzahl der Transaktionen im letzten Block ist 2429"
```

First Time Setup
----------------

To run this skill you need to do two things. The first is to deploy the example code in lambda, and the second is to configure the Alexa skill to use Lambda.

### AWS Lambda Setup

1. Go to the AWS Console and click on the Lambda link. Note: ensure you are in us-east or you wont be able to use Alexa with Lambda.
2. Click on the Create a Lambda Function or Get Started Now button.
3. Skip the blueprint
4. Configure Triggers Screen click the outlined empty square and select Alexa Skill Kit.  Click Next
5. Name the Lambda Function "BlockchainExplorer".
6. Select the runtime as Java 8
7. Download or clone this repository and run `docker run --rm -v /path/to/pom-dir:/usr/src/mymaven philippgille/alexa-java-builder`. This will generate the zip file `/path/to/pom-dir/target/alexa-blockchain-explorer-1.0-jar-with-dependencies.jar`.
8. Select Code entry type as "Upload a .ZIP file" and then upload the `alexa-blockchain-explorer-1.0-jar-with-dependencies.jar` file from the build directory to Lambda
9. Set the Handler as `com.philippgille.alexa.BlockchainExplorerSpeechletRequestStreamHandler` (this refers to the Lambda RequestStreamHandler file in the zip).
10. Create a basic execution role and click create.
11. Leave the Advanced settings as the defaults.
12. Click "Next" and review the settings then click "Create Function"
13. Copy the ARN from the top right to be used later in the Alexa Skill Setup.

### Alexa Skill Setup

1. Go to the [Alexa Console](https://developer.amazon.com/edw/home.html) and click Add a New Skill.
2. Set "Blockchain Explorer" as the skill name and "block explorer" as the invocation name, this is what is used to activate your skill. For example you would say: "Alexa, tell block explorer to say hello."
3. Select the Lambda ARN for the skill Endpoint and paste the ARN copied from above. Click Next.
4. Copy the Intent Schema from the included IntentSchema.json.
5. Copy the Sample Utterances from the included SampleUtterances.txt. Click Next.
6. Go back to the skill Information tab and copy the appId. Paste the appId into a Lambda function code configuration environment variable called `APPLICATION_ID`. This step makes sure the lambda function only serves request from authorized source.
7. You are now able to start testing your sample skill! You should be able to go to the [Echo webpage](http://echo.amazon.com/#skills) and see your skill enabled.
8. In order to test it, try to say some of the Sample Utterances from the Examples section below.
9. Your skill is now saved and once you are finished testing you can continue to publish your skill.

Subsequent iterations / builds
------------------------------

When the Lambda function and Alexa skill are set up according to the *First Time Setup* section above, subsequent iterations are much easier:

1. Change the intent schema, utterances and code
2. Package the code to a JAR with Maven: 'docker run --rm -v /path/to/pom-dir:/usr/src/mymaven philippgille/alexa-java-builder'
3. Upload the JAR to the Lambda function: [https://eu-west-1.console.aws.amazon.com/lambda/home?region=eu-west-1#/functions/BlockchainExplorer?tab=code](https://eu-west-1.console.aws.amazon.com/lambda/home?region=eu-west-1#/functions/BlockchainExplorer?tab=code)
4. Update the intent schema and utterances of the Alexa skill: [https://developer.amazon.com/edw/home.html#/skill/amzn1.ask.skill.\<some-id\>/de_DE/intentSchema](https://developer.amazon.com/edw/home.html#/skill/amzn1.ask.skill.\<some-id\>/de_DE/intentSchema) (replace `<some-id>` with your skill's ID)

License
-------

- Note that this software is licensed under the GNU General Public License version 3. See the `LICENSE` file for details.
- Some used software is licensed under the Apache Software License version 2.0. See the `NOTICE` file for details.

Resources
--------

Here are a few direct links to the Alexa skill documentation:

- [Using the Alexa Skills Kit Samples](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/using-the-alexa-skills-kit-samples)
- [Getting Started](https://developer.amazon.com/appsandservices/solutions/alexa/alexa-skills-kit/getting-started-guide)
- [Invocation Name Guidelines](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/choosing-the-invocation-name-for-an-alexa-skill)
- [Developing an Alexa Skill as an AWS Lambda Function](https://developer.amazon.com/appsandservices/solutions/alexa/alexa-skills-kit/docs/developing-an-alexa-skill-as-a-lambda-function)

Info about the Docker container used to build the skill JAR:

- [https://hub.docker.com/r/philippgille/alexa-java-builder/](https://hub.docker.com/r/philippgille/alexa-java-builder/)