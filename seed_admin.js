/**
 * Namma HomeStay — Firestore Seed Script
 * Uses Firebase Admin SDK via Application Default Credentials
 * Run: node seed_admin.js
 */

const { initializeApp, cert, applicationDefault } = require('firebase-admin/app');
const { getFirestore } = require('firebase-admin/firestore');

// Initialize with project ID — uses Firebase CLI login credentials
initializeApp({
  projectId: 'mindmatrix-proj1'
});

const db = getFirestore();

async function seed() {
  console.log('\n🔥 Namma HomeStay — Seeding Firestore Collections\n');
  console.log('   Project: mindmatrix-proj1\n');

  const batch = db.batch();

  // ── _app_meta ──────────────────────────────────────────────────────────────
  console.log('📁 _app_meta');
  batch.set(db.collection('_app_meta').doc('bootstrap'), {
    initialized: true,
    appVersion: '1.0.0',
    note: 'Created by seed script'
  });

  // ── users ──────────────────────────────────────────────────────────────────
  console.log('📁 users');
  batch.set(db.collection('users').doc('_placeholder'), {
    _note: 'Placeholder — real docs created on user registration',
    uid: '', name: '', email: '', phone: '',
    role: '', dob: '', language: 'English',
    notificationsEnabled: true
  });

  // ── homestays ──────────────────────────────────────────────────────────────
  console.log('📁 homestays');
  batch.set(db.collection('homestays').doc('_placeholder'), {
    _note: 'Placeholder — real docs created when providers save profiles',
    providerId: '', name: 'Sample Malnad Homestay',
    ownerName: '', contact: '',
    address: 'Chikmagalur, Karnataka',
    description: 'Nestled in the heart of the Malnad region',
    photoUrls: [], category: 'Forest',
    latitude: 13.3161, longitude: 75.7720,
    rating: 0.0, reviewCount: 0,
    checklist: {
      'Clean Rooms': false, 'Hygienic Food': false,
      'Safe Drinking Water': false, 'Parking Available': false,
      'Family Friendly': false
    },
    menu: { breakfast: '', lunch: '', dinner: '', todaySpecial: '' },
    pricing: { pricePerDay: '0', isAvailable: false, guestLimit: 0, extraGuestCharge: '0' }
  });

  // ── inquiries ──────────────────────────────────────────────────────────────
  console.log('📁 inquiries');
  batch.set(db.collection('inquiries').doc('_placeholder'), {
    _note: 'Placeholder — real docs created when travelers send inquiries',
    inquiryId: '', travelerId: '', travelerName: '',
    providerId: '', homeStayName: '',
    message: '', checkIn: '', checkOut: '',
    guests: 1, status: 'pending'
  });

  // ── app_config ─────────────────────────────────────────────────────────────
  console.log('📁 app_config');
  batch.set(db.collection('app_config').doc('categories'), {
    _note: 'Global category list — update to sync across both portals',
    available: ['Forest', 'Mountain', 'Coffee', 'Heritage', 'Estate', 'River', 'Valley']
  });

  await batch.commit();

  console.log('\n✅ All 5 collections created successfully!\n');
  console.log('   👉 https://console.firebase.google.com/project/mindmatrix-proj1/firestore\n');
  console.log('   Refresh your Firebase Console to see the collections.\n');
}

seed().catch(err => {
  console.error('\n❌ Error:', err.message);
  process.exit(1);
});
