"""
Vercel Serverless Function entry point.
Wraps the FastAPI backend app for Vercel's Python runtime.
"""

import sys
import os

# Add the backend directory to the Python path so that
# main.py's internal imports (models.*, services.*) resolve correctly.
backend_dir = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "backend")
if backend_dir not in sys.path:
    sys.path.insert(0, backend_dir)

# Now import the FastAPI app — Vercel will detect this ASGI app automatically.
from main import app
