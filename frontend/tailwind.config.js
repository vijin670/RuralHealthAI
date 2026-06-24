/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          dark: '#0D7377',
          DEFAULT: '#14A3A8',
          light: '#4DD0E1',
          container: '#1A3A4A',
        },
        secondary: {
          DEFAULT: '#44B78B',
          light: '#81C784',
          container: '#1B3D2F',
        },
        background: {
          dark: '#0A1628',
          card: '#162D50',
          surface: '#112240',
          variant: '#1A3050'
        },
        text: {
          primary: '#E8ECF4',
          secondary: '#A8B4C8',
          tertiary: '#6B7A94'
        },
        triage: {
          selfCare: '#4CAF50',
          selfCareBg: '#1B3D1F',
          clinic: '#FFC107',
          clinicBg: '#3D3520',
          hospital: '#FF9800',
          hospitalBg: '#3D2A10',
          emergency: '#F44336',
          emergencyBg: '#3D1515'
        },
        accent: {
          blue: '#64B5F6',
          red: '#EF5350',
          green: '#66BB6A'
        }
      },
      fontFamily: {
        sans: ['Inter', 'Outfit', 'sans-serif'],
      },
      keyframes: {
        float: {
          '0%, 100%': { transform: 'translateY(0) translateX(0) rotate(0deg)' },
          '50%': { transform: 'translateY(-30px) translateX(15px) rotate(180deg)' },
        },
        'float-reverse': {
          '0%, 100%': { transform: 'translateY(0) translateX(0) rotate(0deg)' },
          '50%': { transform: 'translateY(30px) translateX(-15px) rotate(-180deg)' },
        },
        drift: {
          '0%': { transform: 'translate(0, 0)' },
          '50%': { transform: 'translate(2%, 2%)' },
          '100%': { transform: 'translate(0, 0)' },
        },
        'pulse-glow': {
          '0%, 100%': { opacity: 0.08, transform: 'scale(1)' },
          '50%': { opacity: 0.15, transform: 'scale(1.05)' },
        }
      },
      animation: {
        'pulse-fast': 'pulse 1s cubic-bezier(0.4, 0, 0.6, 1) infinite',
        'spin-slow': 'spin 20s linear infinite',
        'float-slow': 'float 12s ease-in-out infinite',
        'float-slower': 'float-reverse 18s ease-in-out infinite',
        'drift-slow': 'drift 20s ease-in-out infinite',
        'pulse-glow': 'pulse-glow 8s ease-in-out infinite',
      }
    },
  },
  plugins: [],
}
