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


echo
echo "   Get all Ads Name API"
echo "   --------------------------------"
curl -sX GET "${machine}/api/advertisers/all"  | python -mjson.tool
echo

echo
echo "    POST Create Movie"
echo "   --------------------------------"
curl -s -H "Content-Type: application/json" \
	-X POST "${machine}/api/advertisers/?name=ThisWillBeDone&contactName=Kids&creditAmount=2.5"

# Format doesn't work
#curl -s -H "Content-Type: application/json" -X POST "${machine}/api/advertisers" \
#	--data "name=ToyStory" \
#	--data "contactName=kids" \
#	--data "creditAmount=3.5"
echo

echo
echo "   Get Ads by ID: 32f5b01-c1f9-4ff1-b9a9-7777ef8392c2"
echo "   --------------------------------"
curl -s -H "Content-Type: application/json" -X GET "${machine}/api/advertisers/95cf6e3e-f25d-4a5d-8122-4ab39e54a3d7"
echo

echo
echo "    PUT Update Ad: 7bd6e7a3-7b00-49e5-a3df-1d56173386dd"
echo "   --------------------------------"
curl -s -H "Content-Type: application/json" -X PUT "${machine}/api/advertisers/?id=232f5b01-c1f9-4ff1-b9a9-7777ef8392c2&name=Toy%20Story4&contactName=Family&creditAmount=101.5"
#// | python -mjson.tool
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
echo "    Delete Ad: 3f0dfb94-848e-44f3-8dec-5e083f39922d"
echo "   --------------------------------"
#curl -s -H "Content-Type: application/json" -X DELETE "${machine}/api/advertisers/"  \
#	--data "id=1L" | python -mjson.tool
#curl -sX DELETE "${machine}/api/advertiser/?id=1"  \
#> tmp.html
curl -s -H "Content-Type: application/json" -X DELETE "${machine}/api/advertisers/95cf6e3e-f25d-4a5d-8122-4ab39e54a3d7"
#// 3f0dfb94-848e-44f3-8dec-5e083f39922d" | python -mjson.tool
echo

echo
echo "   Verifying Advertisers has been deleted"
echo "   --------------------------------"
curl -sX GET "${machine}/api/advertisers/all"  | python -mjson.tool
echo
