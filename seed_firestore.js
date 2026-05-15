const https = require('https');

const PROJECT_ID = 'mindmatrix-proj1';
const API_KEY = 'AIzaSyC1drsWgir00boVs-MP0U7avfYT53iHi6k';
const BASE_URL = `firestore.googleapis.com`;
const DB_PATH = `projects/${PROJECT_ID}/databases/(default)/documents`;

function firestoreRequest(method, path, body) {
  return new Promise((resolve, reject) => {
    const bodyStr = body ? JSON.stringify(body) : '';
    const options = {
      hostname: BASE_URL,
      path: `/v1/${path}?key=${API_KEY}`,
      method,
      headers: {
        'Content-Type': 'application/json',
        'Content-Length': Buffer.byteLength(bodyStr)
      }
    };

    const req = https.request(options, (res) => {
      let data = '';
      res.on('data', (chunk) => data += chunk);
      res.on('end', () => {
        if (res.statusCode >= 200 && res.statusCode < 300) {
          resolve(JSON.parse(data));
        } else {
          reject(new Error(`HTTP ${res.statusCode}: ${data}`));
        }
      });
    });

    req.on('error', reject);
    if (bodyStr) req.write(bodyStr);
    req.end();
  });
}

function strVal(v)  { return { stringValue: v }; }
function boolVal(v) { return { booleanValue: v }; }
function intVal(v)  { return { integerValue: String(v) }; }
function dblVal(v)  { return { doubleValue: v }; }
function arrVal(items) { return { arrayValue: { values: items } }; }
function mapVal(fields) { return { mapValue: { fields } }; }
function nullVal()  { return { nullValue: null }; }

async function createDoc(collection, docId, fields) {
  const path = `${DB_PATH}/${collection}/${docId}`;
  try {
    await firestoreRequest('PATCH', path, { fields });
    console.log(`  ✓  ${collection}/${docId}`);
  } catch (e) {
    console.error(`  ✗  ${collection}/${docId} — ${e.message.slice(0, 120)}`);
  }
}

async function main() {
  console.log('\n🔥 Namma HomeStay — Bootstrapping Firestore Collections\n');
  console.log(`   Project: ${PROJECT_ID}\n`);

  // ── 1. _app_meta / bootstrap ─────────────────────────────────────────────
  console.log('📁 _app_meta');
  await createDoc('_app_meta', 'bootstrap', {
    initialized:  boolVal(true),
    appVersion:   strVal('1.0.0'),
    note:         strVal('Created by FirestoreInitializer seed script')
  });

  // ── 2. users / _placeholder ───────────────────────────────────────────────
  console.log('📁 users');
  await createDoc('users', '_placeholder', {
    _note:                strVal('Placeholder — real docs created on user registration'),
    uid:                  strVal(''),
    name:                 strVal(''),
    email:                strVal(''),
    phone:                strVal(''),
    role:                 strVal(''),
    dob:                  strVal(''),
    language:             strVal('English'),
    notificationsEnabled: boolVal(true)
  });

  // ── 3. homestays / _placeholder ───────────────────────────────────────────
  console.log('📁 homestays');
  await createDoc('homestays', '_placeholder', {
    _note:       strVal('Placeholder — real docs created when providers save their profiles'),
    providerId:  strVal(''),
    name:        strVal('Sample Malnad Homestay'),
    ownerName:   strVal(''),
    contact:     strVal(''),
    address:     strVal('Chikmagalur, Karnataka'),
    description: strVal('Nestled in the heart of the Malnad region'),
    photoUrls:   arrVal([]),
    category:    strVal('Forest'),
    latitude:    dblVal(13.3161),
    longitude:   dblVal(75.7720),
    rating:      dblVal(0.0),
    reviewCount: intVal(0),
    checklist: mapVal({
      'Clean Rooms':        boolVal(false),
      'Hygienic Food':      boolVal(false),
      'Safe Drinking Water':boolVal(false),
      'Parking Available':  boolVal(false),
      'Family Friendly':    boolVal(false)
    }),
    menu: mapVal({
      breakfast:    strVal(''),
      lunch:        strVal(''),
      dinner:       strVal(''),
      todaySpecial: strVal('')
    }),
    pricing: mapVal({
      pricePerDay:       strVal('0'),
      isAvailable:       boolVal(false),
      guestLimit:        intVal(0),
      extraGuestCharge:  strVal('0')
    })
  });

  // ── 4. inquiries / _placeholder ───────────────────────────────────────────
  console.log('📁 inquiries');
  await createDoc('inquiries', '_placeholder', {
    _note:        strVal('Placeholder — real docs created when travelers send inquiries'),
    inquiryId:    strVal(''),
    travelerId:   strVal(''),
    travelerName: strVal(''),
    providerId:   strVal(''),
    homeStayName: strVal(''),
    message:      strVal(''),
    checkIn:      strVal(''),
    checkOut:     strVal(''),
    guests:       intVal(1),
    status:       strVal('pending')
  });

  // ── 5. app_config / categories ────────────────────────────────────────────
  console.log('📁 app_config');
  await createDoc('app_config', 'categories', {
    _note:     strVal('Global category list — update here to sync across both portals'),
    available: arrVal([
      strVal('Forest'),
      strVal('Mountain'),
      strVal('Coffee'),
      strVal('Heritage'),
      strVal('Estate'),
      strVal('River'),
      strVal('Valley')
    ])
  });

  console.log('\n✅ Bootstrap complete! Open your Firebase Console and refresh Firestore.\n');
  console.log('   https://console.firebase.google.com/project/mindmatrix-proj1/firestore\n');
}

main().catch(e => {
  console.error('\n❌ Fatal error:', e.message);
  process.exit(1);
});
