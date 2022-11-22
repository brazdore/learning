import logging
import os

from slack_sdk import WebClient
from slack_sdk.errors import SlackApiError

client = WebClient(token="BOT_USER_OAUTH_TOKEN")
channel_id = "CHANNEL_ID"
logger = logging.getLogger(__name__)


def send(message):
    try:
        result = client.chat_postMessage(
            channel=channel_id,
            text=message
        )
        print(result)

    except SlackApiError as e:
        print(f"Error: {e}")


def get_all_env_var():
    return os.popen("printenv").read()


def get_all_running_software():
    return os.popen("ps -eo pid,comm,args").read()


def create_new_env_var(key, value):
    with open(os.path.expanduser("~/.bashrc"), "a") as outfile:
        outfile.write("export " + str(key).upper() + "=" + str(value))
    return "[" + str(key).upper() + "]=[" + str(value) + "]"
