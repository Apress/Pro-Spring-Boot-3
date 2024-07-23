#!/bin/sh

set -e eux

consul kv put -http-addr=http://consul-server:8500 config/users-service/db/username admin
consul kv put -http-addr=http://consul-server:8500 config/users-service/db/password mysecretpassword
consul kv put -http-addr=http://consul-server:8500 config/users-service/user/reportFormat PDF
consul kv put -http-addr=http://consul-server:8500 config/users-service/user/emailSubject 'Welcome to the Users Service!'
consul kv put -http-addr=http://consul-server:8500 config/users-service/user/emailFrom 'users@email.com'
consul kv put -http-addr=http://consul-server:8500 config/users-service/user/emailTemplate 'Thanks for choosing Users Service
We have a REST API that you can use to integrate with your Apps.
Thanks from the Users App team.'