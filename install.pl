#!/usr/bin/env perl 
#===============================================================================
#
#         FILE: install.pl
#
#        USAGE: ./install.pl  
#
#  DESCRIPTION: 
#
#      OPTIONS: ---
# REQUIREMENTS: ---
#         BUGS: ---
#        NOTES: ---
#       AUTHOR: Jie Feng (http://hey.i-feng.org), jiefeng.hopkins@gmail.com
# ORGANIZATION: 
#      VERSION: 1.0
#      CREATED: 01/29/2014 10:54:33 AM
#     REVISION: ---
#===============================================================================

#use strict;
use warnings;
use utf8;
use feature qw/state say switch/;

if (!open CONFIG, ">NetBeansProjectsServer/CS2/config.properties"){
    die "Can't open config.properties file!($!)" 
}

print "Please provide your username of database:";
chomp(my $username = <STDIN>);
print "Please provide your password of database:";
chomp(my $password = <STDIN>);
my $scheme = "CS2Cloud";


select CONFIG;
say "database_scheme=$scheme";
say "database_username=$username";
say "database_password=$password";

select STDOUT;
my $mysql = "mysql -u $username -p$password";
print `$mysql -e 'create database IF NOT EXISTS $scheme default charset=utf8'`;
print `$mysql $scheme < cloud.sql`;
