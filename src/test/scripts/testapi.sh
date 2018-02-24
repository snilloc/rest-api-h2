#
# Test Each Advertiser API using Curl Commands
#
# -------------------------------------

if [ $# == 1 ]
then
   machine=http://127.0.0.1:8080
   token=$1
elif [ $# == 2 ]
then
   machine=http://$1
   token=$2
else
  echo "Usage: "
  echo "     test-api.sh <Advertiser server or http://localhost:8080>> <encoded token> "
  echo ""
  exit 0
fi

echo "   Get Ads Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/advertiser?id=16af8093-e43b-4756-8d2b-c214ecac6256"
echo

echo
echo "   Get all Ads Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/advertisers/all"  | python -mjson.tool
echo

echo
echo "    POST Create Movie"
echo "   --------------------------------"
#curl -s -H "Content-Type: application/json" \
#	-X POST "${machine}/api/advertisers/?name=ThisWillBeDone&contactName=Kids&creditAmount=2.5"
curl -s -H "Content-Type: application/json" -X POST "${machine}/api/advertisers" \
	--data "name=ToyStory" \
	--data "contactName=kids" \
	--data "creditAmount=3.5"
echo


echo
echo "    PUT Update Ad: 7bd6e7a3-7b00-49e5-a3df-1d56173386dd"
echo "   --------------------------------"
curl -s -H "Content-Type: application/json" -X PUT "${machine}/api/advertisers/update?id=7bd6e7a3-7b00-49e5-a3df-1d56173386dd&name=Toy%20Story&contactName=Family&creditAmount=1.5" | python -mjson.tool
#	--data "name=Toy%20Story" \
#	--data "genre=Family" \
#	--data "year=2001" \
#	--data "rating=3.5" | python -mjson.tool
echo

echo
echo
echo "   Get all Advertisers Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/advertisers/all"  | python -mjson.tool
echo

echo
echo "    Delete Ad: 9492a56c-87f8-4015-8810-23bb3743fedf"
echo "   --------------------------------"
#curl -s -H "Content-Type: application/json" -X DELETE "${machine}/api/advertisers/"  \
#	--data "id=1L" | python -mjson.tool
#curl -sX DELETE "${machine}/api/advertiser/?id=1"  \
#> tmp.html
curl -sX DELETE "${machine}/api/advertisers/9492a56c-87f8-4015-8810-23bb3743fedf" | python -mjson.tool
echo

echo
echo "   Verifying Advertisers has been deleted"
echo "   --------------------------------"
curl -sX GET "${machine}/api/advertisers"  | python -mjson.tool
echo
